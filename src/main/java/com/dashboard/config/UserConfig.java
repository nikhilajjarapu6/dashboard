package com.dashboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.dashboard.service.CustomUser;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class UserConfig  {
	
	@Autowired
	CustomUser userDetailes;
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		return http
				.csrf(csrf->csrf.disable())
				.authorizeHttpRequests(auth->auth
				.requestMatchers("/app/login").permitAll()
				.anyRequest().hasRole("ADMIN"))
				.exceptionHandling(ex -> ex
			            .accessDeniedHandler((request, response, accessDeniedException) -> {
			                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			                response.setContentType("application/json");
			                response.getWriter().write("{\"error\": \"Access denied: You do not have the required role.\"}");
			            })
			        )
				.httpBasic(Customizer.withDefaults())
//				.formLogin(form -> form
//		                .loginPage("/login.html")       // our custom HTML page
//		                .loginProcessingUrl("/app/login")   // Spring will handle this POST request
//		                .defaultSuccessUrl("/app/profile", true) 
//		                .permitAll() 
//		            )
				.userDetailsService(userDetailes)
				.build();
	}
}
