package org.ll.context;

import java.util.ArrayList;
import java.util.List;

import org.business.models.User;
import org.business.models.UserRole;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Configuration
@EnableWebSecurity
public class CASSecurityConfig extends WebSecurityConfigurerAdapter {

	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.addFilter(casAuthenticationFilter())
		.authorizeRequests()
		.antMatchers("").permitAll()
		;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(casAuthenticationProvider());
	}
	
	@Bean
	CasAuthenticationProvider casAuthenticationProvider(){
		CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
		casAuthenticationProvider.setAuthenticationUserDetailsService(new UserDetailsByNameServiceWrapper(userDetailsService()));
		casAuthenticationProvider.setServiceProperties(serviceProperties());
		casAuthenticationProvider.setTicketValidator(new Cas20ServiceTicketValidator("https://localhost:8443/cas"));
		casAuthenticationProvider.setKey("key4CasAuthenticationProvider");
		return casAuthenticationProvider;
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
	CasAuthenticationEntryPoint casAuthenticationEntryPoint(ServiceProperties serviceProperties){
		CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
		casAuthenticationEntryPoint.setLoginUrl("https://localhost:8443/cas/login");
		casAuthenticationEntryPoint.setServiceProperties(serviceProperties);
		return casAuthenticationEntryPoint;
	}
	
	@Bean
	ServiceProperties serviceProperties(){
		ServiceProperties serviceProperties = new ServiceProperties();
		serviceProperties.setService("http://localhost:9090/j_spring_cas_security_check");
		return serviceProperties;
	}
	
	@Bean
	CasAuthenticationFilter casAuthenticationFilter(){
		CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
		casAuthenticationFilter.setFilterProcessesUrl("/j_spring_cas_security_check");
		casAuthenticationFilter.setAuthenticationManager(authenticationManager);
	}

	
}
