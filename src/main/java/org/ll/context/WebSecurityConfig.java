package org.ll.context;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.Header;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
//@EnableOAuth2Sso
@EnableOAuth2Client
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private OAuth2ClientContext oauth2ClientContext;

//    @Autowired
//    private AuthorizationCodeResourceDetails client;

//    @Autowired
//    private ResourceServerProperties resource;
    
    @Value("${keystore.clientCert.file}")
    private String clientKeystore;
    
    @Value("${keystore.clientCert.password}")
    private String clientKeystorePwd;
    
    @Value("${keystore.clientCert.alias}")
    private String clientKeystoreAlias;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
            .antMatchers("/login**", "/webjars/**", "/css/**", "/js/**").permitAll()
            .anyRequest().hasRole("FRONT_USER")
        .and()
        	.formLogin().permitAll()
        	.successHandler(new SavedRequestAwareAuthenticationSuccessHandler(){

				@Override
				public void onAuthenticationSuccess(HttpServletRequest request,
						HttpServletResponse response,
						Authentication authentication) throws ServletException,
						IOException {
					HttpSession session = request.getSession(false);
					if(session != null){
						session.setMaxInactiveInterval(600);
					}
					super.onAuthenticationSuccess(request, response, authentication);
				}
        		
        	})
//        .and()
//            .exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
        .and()
            .logout()
            .logoutSuccessHandler(new LogoutSuccessHandler(){
                @Override
                public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                    throws IOException, ServletException {
//                    callAPIService.deleteToken();
                    HttpSession httpSession = request.getSession(false);
                    for(Cookie cookie : request.getCookies()){
                        log.debug("remove cookie[" + cookie.getName() + ":" + cookie.getValue() + "]");
                        cookie.setValue(null);
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                    log.debug("httpSession:" + httpSession);
                    if(httpSession != null){
                        log.debug("httpSession.removeAttribute");
                        httpSession.removeAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
                        httpSession.invalidate();
                    }
                }
                
            })
//            .addLogoutHandler(new LogoutHandler(){
//                @Override
//                public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//                    callAPIService.deleteToken();
//                    HttpSession httpSession = request.getSession(false);
//                    log.debug("httpSession:" + httpSession);
//                    for(Cookie cookie : request.getCookies()){
//                        cookie.setValue(null);
//                        cookie.setMaxAge(0);
//                        response.addCookie(cookie);
//                    }
//                    if(httpSession != null){
//                        log.debug("httpSession.removeAttribute");
//                        httpSession.removeAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
//                        httpSession.invalidate();
//                    }
//                }
//            })
            .permitAll()
        .and()
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .ignoringAntMatchers("/logout")
        .and()
            .headers().xssProtection()
        .and()
            .frameOptions().sameOrigin()
            ;
    }

    @Override 
    public void configure(WebSecurity web) throws Exception { 
         web.ignoring().antMatchers("/resources/**", "/static/**", "/webjars/**", "/images/**"); 
    } 

    @Bean
    OAuth2RestOperations restTemplate(RestTemplateBuilder builder) {
    	
    	return builder.additionalCustomizers(new RestTemplateCustomizer(){

			@Override
			public void customize(RestTemplate restTemplate) {
				Certificate cert = null;
				try {
					KeyStore keystore = KeyStore.getInstance("JKS");
					keystore.load(new ClassPathResource(clientKeystore).getInputStream(), clientKeystorePwd.toCharArray());
					cert = keystore.getCertificate(clientKeystoreAlias);
					
					// header
					List<Header> headers = new ArrayList<>(1);
			    	headers.add(new BasicHeader("clientCert", new String(cert.getEncoded())));
			    	
					restTemplate.setRequestFactory(
							new HttpComponentsClientHttpRequestFactory(
								HttpClientBuilder.create()
									.setDefaultHeaders(Collections.unmodifiableList(headers))
								.build()
								));
				} catch (KeyStoreException | NoSuchAlgorithmException
						| CertificateException | IOException e) {
					log.error("fail to add client cert to header!!", e);
				}
		    	
				
			}
    		
    	}).configure(new OAuth2RestTemplate(apiClient(), oauth2ClientContext));
    }
    
    
    
    @Bean
    @Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
    	auth.inMemoryAuthentication()
    	.withUser("f").password("p").roles("FRONT_USER")
    	;
	}

//    @Bean
//    @ConfigurationProperties("api.client")
//    public AuthorizationCodeResourceDetails apiClient() {
//    	return new AuthorizationCodeResourceDetails();
//    }

    @Bean
    @ConfigurationProperties("api.client")
    ResourceOwnerPasswordResourceDetails apiClient() {
    	return new ResourceOwnerPasswordResourceDetails();
    }

    
    @Bean
    @ConfigurationProperties("api.resource")
    public ResourceServerProperties apiResource() {
      return new ResourceServerProperties();
    }
    
}
