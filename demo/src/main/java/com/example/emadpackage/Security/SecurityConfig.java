package com.example.emadpackage.Security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Service;

@Service
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/category/**").hasAnyRole("USER")
			.antMatchers("/").permitAll();
		
//Both the codes are same. Above is a standard way and below we are using spring expression language to achieve the same.		
		
//		http
//		.authorizeRequests()
//		.antMatchers("/category/**").access("hasRole('ROLE_USER')")
//		.antMatchers("/").permitAll();

			
	}
}
