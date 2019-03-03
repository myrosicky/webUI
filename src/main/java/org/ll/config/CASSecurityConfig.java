package org.ll.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextListener;

import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


//@Configuration
//@EnableWebSecurity
public class CASSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		
		// CAS logout filter should stand before casAuthenticationFilter
		.addFilterBefore(casLogoutFilter(), casAuthenticationFilter().getClass()) 
		.addFilter(casAuthenticationFilter())
		.authorizeRequests()
			.antMatchers("/login**", "/webjars/**", "/css/**", "/js/**").permitAll()
            .anyRequest().authenticated()
            .and()
		.exceptionHandling()
			.authenticationEntryPoint(casAuthenticationEntryPoint())
		;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(casAuthenticationProvider());
	}
	
	
	@Bean
	protected UserDetailsService userDetailsService(){
		return new UserDetailsService(){

			@Override
			public UserDetails loadUserByUsername(String username)
					throws UsernameNotFoundException {
				
				List<SimpleGrantedAuthority> roles = new ArrayList<>(1);
				roles.add(new SimpleGrantedAuthority("ROLE_APIUSER"));
				
				return new org.springframework.security.core.userdetails.User(username, "", roles);
			}
			
		};
	}

	@Bean
	CasAuthenticationEntryPoint casAuthenticationEntryPoint(){
		CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
		casAuthenticationEntryPoint.setLoginUrl("https://localhost:8443/cas/login");// CAS server url
		casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
		return casAuthenticationEntryPoint;
	}
	
	@Bean
	ServiceProperties serviceProperties(){
		ServiceProperties serviceProperties = new ServiceProperties();
		// the redirect url after CAS logon, add a a new filter to liston on /j_spring_cas_security_check.
		serviceProperties.setService("http://localhost:9090/j_spring_cas_security_check");
		return serviceProperties;
	}
	
	@Bean
    @Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Bean
	CasAuthenticationFilter casAuthenticationFilter() throws Exception{
		CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
		casAuthenticationFilter.setFilterProcessesUrl("/j_spring_cas_security_check");
		casAuthenticationFilter.setAuthenticationManager(authenticationManager());
		return casAuthenticationFilter;
	}
	
	@Bean
	org.jasig.cas.client.session.SingleSignOutFilter casLogoutFilter(){
		return new org.jasig.cas.client.session.SingleSignOutFilter();
	}

	@Bean
	CasAuthenticationProvider casAuthenticationProvider(){
		CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
		casAuthenticationProvider.setAuthenticationUserDetailsService(new UserDetailsByNameServiceWrapper(userDetailsService()));
		casAuthenticationProvider.setServiceProperties(serviceProperties());
		
		// the ticket validation is handled by CAS server, so fill in CAS server root path
		casAuthenticationProvider.setTicketValidator(new Cas20ServiceTicketValidator("https://localhost:8443/cas"));
		casAuthenticationProvider.setKey("key4CasAuthenticationProvider");
		return casAuthenticationProvider;
	}
	
	  @Bean
	  public ServletListenerRegistrationBean<org.jasig.cas.client.session.SingleSignOutHttpSessionListener> listenerRegistrationBean() {
	    ServletListenerRegistrationBean<org.jasig.cas.client.session.SingleSignOutHttpSessionListener> bean = 
	        new ServletListenerRegistrationBean<>();
	    bean.setListener(new org.jasig.cas.client.session.SingleSignOutHttpSessionListener());
	    return bean;

	  }
	
}
