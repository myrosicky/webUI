package org.ll.context;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
//@EnableOAuth2Sso
@EnableOAuth2Client
public class WebSecurityContext extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(WebSecurityContext.class);

    @Autowired
    private OAuth2ClientContext oauth2ClientContext;

//    @Autowired
//    private AuthorizationCodeResourceDetails client;

//    @Autowired
//    private ResourceServerProperties resource;
    
//    @Autowired(required=false)
//    @Lazy
//    private CallAPIService callAPIService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
            .antMatchers("/login**", "/webjars/**", "/css/**", "/js/**").permitAll()
            .anyRequest().hasRole("FRONT_USER")
        .and()
        	.formLogin().permitAll()
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
//        return new OAuth2RestTemplate(client, oauth2ClientContext);
//        MyOAuth2RestTemplate restTemplate = new MyOAuth2RestTemplate(client, oauth2ClientContext);
//        restTemplate.setErrorHandler(new MyResponseErrorHandler());
//    	return restTemplate;
    	return builder.configure(new OAuth2RestTemplate(apiClient(), oauth2ClientContext));
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
