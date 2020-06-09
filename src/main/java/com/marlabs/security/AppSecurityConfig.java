package com.marlabs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public AuthenticationProvider authProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());

		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
		.authorizeRequests().antMatchers("/login").permitAll()
		.antMatchers("/reg").permitAll()
		.antMatchers("/user").hasAnyRole("user")		
		.antMatchers("/admin").hasRole("admin")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.and()
		.logout().logoutSuccessUrl("/logout1").permitAll()
		
		;
	}
	
	

	/*
	 * @Bean
	 * 
	 * @Override protected UserDetailsService userDetailsService() {
	 * 
	 * 
	 * List<UserDetails> users=new ArrayList<UserDetails>();
	 * 
	 * users.add(User.withDefaultPasswordEncoder().username("abc").password("123").
	 * roles("USER").build());
	 * 
	 * return new InMemoryUserDetailsManager(users); }
	 */

}
