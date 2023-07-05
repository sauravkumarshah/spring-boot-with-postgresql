package com.kantar.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.kantar.template.service.UserInfoService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	// authentication
	@Bean
//	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
	public UserDetailsService userDetailsService() {
//		UserDetails admin = User.withUsername("admin").password(encoder.encode("password")).roles("ADMIN").build();
//		UserDetails user = User.withUsername("user").password(encoder.encode("pwd")).roles("USER").build();
//		return new InMemoryUserDetailsManager(admin, user);

		return new UserInfoService();

	}

	// authorization
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(CsrfConfigurer::disable)
				.authorizeHttpRequests(
						authorizeHttpRequests -> authorizeHttpRequests.requestMatchers("/api/v1/user").permitAll())
				.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
						.requestMatchers("/api/v1/employees", "/api/v1/employees/*").authenticated())
				.formLogin(Customizer.withDefaults()).build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
}
