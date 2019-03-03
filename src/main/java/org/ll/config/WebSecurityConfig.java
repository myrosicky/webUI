package org.ll.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private OAuth2ClientContext oauth2ClientContext;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
            .antMatchers("/", "/login**", "/webjars/**", "/css/**", "/js/**").permitAll()
            .anyRequest().hasAnyRole("API_USER", "USER")
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
            .csrf()
            .disable()
//            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//            .ignoringAntMatchers("/logout")
//        .and()
//            .headers().xssProtection()
//        .and()
//            .frameOptions().sameOrigin()
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
    
}
