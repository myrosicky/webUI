package org.ll.config;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.Filter;

import org.apache.http.Header;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
//@EnableOAuth2Sso
@EnableOAuth2Client
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
            .antMatchers("/login**", "/webjars/**", "/css/**", "/js/**").permitAll()
            .anyRequest().hasAnyRole("API_USER", "ADMIN", "USER")
            .and()
            	.exceptionHandling()
					.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
					
//        .and()
//            .logout()
//            .logoutSuccessHandler(new LogoutSuccessHandler(){
//                @Override
//                public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
//                    throws IOException, ServletException {
//                    HttpSession httpSession = request.getSession(false);
//                    for(Cookie cookie : request.getCookies()){
//                        log.debug("remove cookie[" + cookie.getName() + ":" + cookie.getValue() + "]");
//                        cookie.setValue(null);
//                        cookie.setMaxAge(0);
//                        response.addCookie(cookie);
//                    }
//                    log.debug("httpSession:" + httpSession);
//                    if(httpSession != null){
//                        log.debug("httpSession.removeAttribute");
//                        httpSession.removeAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
//                        httpSession.invalidate();
//                    }
//                }
//                
//            })
//            .permitAll()
        .and()
        	.logout().logoutSuccessUrl("/").permitAll()
        .and()
        	.csrf()      
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//            .ignoringAntMatchers("/logout")
//        .and()
//            .headers().xssProtection()
//        .and()
//            .frameOptions().sameOrigin()
        .and()
        	.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
        
            ;
    }

    @Override 
    public void configure(WebSecurity web) throws Exception { 
         web.ignoring().antMatchers("/resources/**", "/static/**", "/webjars/**", "/images/**"); 
    } 
    
    @Bean
    @Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth)
//			throws Exception {
//    	auth.inMemoryAuthentication()
//    	.withUser("f").password("p").roles("FRONT_USER")
//    	;
//	}

    @Bean
    @ConfigurationProperties("api.client")
    public AuthorizationCodeResourceDetails apiClient() {
    	return new AuthorizationCodeResourceDetails();
    }
    
    

//    @Bean
//    @ConfigurationProperties("api.client")
//    ResourceOwnerPasswordResourceDetails apiClient() {
//    	return new ResourceOwnerPasswordResourceDetails();
//    }

    
    @Bean
    @Primary
    @ConfigurationProperties("api.resource")
    public ResourceServerProperties apiResource() {
      return new ResourceServerProperties();
    }
    
    @Bean
    @Primary
    UserInfoTokenServices tokenServices(){
    	UserInfoTokenServices tokenServices = new UserInfoTokenServices(
    			apiResource().getUserInfoUri(), apiClient().getClientId());
    	tokenServices.setRestTemplate(restTemplate);
    	return tokenServices;
    }
    	      
    @Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}	 
    
    private Filter ssoFilter() {
    	  OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter("/login");
    	  facebookFilter.setRestTemplate(restTemplate);
    	  facebookFilter.setTokenServices(tokenServices());
    	  return facebookFilter;
    }
    
    @Autowired
    OAuth2RestOperations restTemplate;
    
}
