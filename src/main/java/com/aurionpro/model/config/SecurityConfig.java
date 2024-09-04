package com.aurionpro.model.config;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aurionpro.model.security.JwtAuthenticationEntryPoint;
import com.aurionpro.model.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors(withDefaults());
		http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

		http.authorizeHttpRequests(request -> request.requestMatchers("/bankapp/register").permitAll());
		http.authorizeHttpRequests(request -> request.requestMatchers("/bankapp/login").permitAll());

		http.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET, "/bankapp/admin/**").hasRole("ADMIN"));
		http.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST, "/bankapp/admin/**").hasRole("ADMIN"));
		http.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.PUT, "/bankapp/admin/**").hasRole("ADMIN"));
		http.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.DELETE, "/bankapp/admin/**").hasRole("ADMIN"));
		
		http.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET, "/bankapp/customer/**").hasRole("CUSTOMER"));
		http.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST, "/bankapp/customer/**").hasRole("CUSTOMER"));
		http.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.PUT, "/bankapp/customer/**").hasRole("CUSTOMER"));
		http.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.DELETE, "/bankapp/customer/**").hasRole("CUSTOMER"));
		
		http.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint));
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
		return http.build();
	}

}
