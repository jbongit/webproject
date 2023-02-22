package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class MyConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable().authorizeHttpRequests().requestMatchers("/**")
				.permitAll()
				.and()
				.authorizeHttpRequests().requestMatchers("/user/**").hasRole("ROLE_USER")
				.and().authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ROLE_ADMIN")
				.anyRequest().authenticated()
				.and().formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/loginCheck")
				.failureUrl("/login?error")
				.successHandler((request, response, authentication) -> {
					CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
					if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
						String targetUrl = "/admin/dashboard";
						response.sendRedirect(targetUrl);
					} else {
						String targetUrl = "/user/" + user.getId();
						response.sendRedirect(targetUrl);
					}
				})
				.and()
				.build();
	}
}
