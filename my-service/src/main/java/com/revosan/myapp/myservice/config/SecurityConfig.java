/**
 * Project: MyService
 * Package: com.revosan.myapp.myservice.config
 * File: SecurityConfig.java
 *
 * Description:
 * This class provides the core Spring Security configuration for the application.
 * It enables web security, defines a security filter chain to handle HTTP requests,
 * configures user authentication (in-memory for demonstration), sets up password encoding,
 * and applies best practices for REST APIs such as stateless session management and CORS.
 * It's designed to be extensible for more advanced authentication mechanisms like JWT or OAuth2.
 *
 * Author: Revosan A. Legaspi
 * Date: June 14, 2025
 * Version: 1.0.0
 *
 * Change Log:
 * V1.0.0 - Initial creation of SecurityConfig for basic authentication.
 * V1.1.0 - Added best practices for REST API: stateless session, CORS, and refined endpoint security.
 */
package com.revosan.myapp.myservice.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * Configures the security filter chain that handles HTTP requests. This method
	 * defines authorization rules for different API endpoints, sets session
	 * management to stateless, and configures CORS.
	 *
	 * @param http HttpSecurity object to configure security settings.
	 * @return A SecurityFilterChain instance.
	 * @throws Exception if an error occurs during configuration.
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// Configure CORS to allow requests from specific origins
				.cors(withDefaults()) // Use withDefaults() to apply the CorsConfigurationSource bean

				// Configure authorization for different HTTP requests
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests
						// Allow unauthenticated access to Swagger UI and API docs endpoints
						// These are typically public for API documentation
						.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**")
						.permitAll()
						// Allow unauthenticated GET requests to the /api/products endpoint
						// Read operations often don't require authentication
						// .requestMatchers(HttpMethod.GET, "/api/products",
						// "/api/products/*").permitAll()
						.requestMatchers("/api/products", "/api/products/*").permitAll()
						// Require authentication for all other requests (POST, PUT, DELETE to
						// /api/products, etc.)
						// This catches any remaining endpoints or methods not explicitly permitted
						.anyRequest().authenticated())
				// Configure HTTP Basic authentication as the primary authentication mechanism
				// This is suitable for demonstration and simple APIs
				.httpBasic(withDefaults())
				// Configure exception handling for unauthenticated and access denied scenarios.
				// For REST APIs, we want to return specific HTTP status codes (e.g., 401, 403)
				// instead of redirecting to login pages.
				.exceptionHandling(exceptions -> exceptions
						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				// You can add .accessDeniedHandler(customAccessDeniedHandler()) for 403
				// Forbidden scenarios
				)
				// Disable CSRF protection. CSRF is typically not needed for stateless REST APIs
				// that rely on token-based authentication (like JWT) as there are no sessions
				// to exploit. Re-enable and configure if using session-based authentication.
				.csrf(csrf -> csrf.disable())
				// Set session management to stateless. This ensures that the server does not
				// create or use HTTP sessions to store client state, which is crucial for
				// scalable RESTful APIs. Each request must contain all necessary authentication
				// information (e.g., via Authorization header).
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

	/**
	 * Configures a CorsConfigurationSource bean to define CORS policies. This
	 * allows the frontend application running on a different origin (e.g., React,
	 * Angular app) to make requests to this backend API. Adjust allowed origins,
	 * methods, and headers based on your frontend deployment.
	 *
	 * @return A UrlBasedCorsConfigurationSource instance with defined CORS rules.
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration configuration = new CorsConfiguration();
		// Allow requests from any origin during development.
		// In production, replace "*" with specific frontend origins (e.g.,
		// "http://localhost:3000", "https://your-frontend.com")
		configuration.setAllowedOrigins(Arrays.asList("*"));
		// Allow all common HTTP methods used by a REST API
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		// Allow all headers, especially "Authorization", "Content-Type", etc.
		configuration.setAllowedHeaders(Arrays.asList("*"));
		// Allow credentials (e.g., cookies, HTTP authentication) to be sent with the
		// request
		// Set to true if your frontend needs to send credentials
		configuration.setAllowCredentials(true);
		// How long the results of a preflight request can be cached
		configuration.setMaxAge(3600L); // 1 hour

		// Apply this CORS configuration to all paths
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	/**
	 * Defines an in-memory user store for demonstration purposes. In a real
	 * application, this would be replaced by a database-backed UserDetailsService
	 * (e.g., using JdbcUserDetailsManager, JPA for custom users, or integrating
	 * with an external identity provider like Okta, Auth0).
	 *
	 * @param passwordEncoder The PasswordEncoder to use for encoding passwords.
	 * @return An InMemoryUserDetailsManager instance with defined users.
	 */
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		UserDetails user = User.builder().username("user").password(passwordEncoder.encode("password")).roles("USER")
				.build();
		UserDetails admin = User.builder().username("admin").password(passwordEncoder.encode("adminpass"))
				.roles("ADMIN", "USER").build();
		return new InMemoryUserDetailsManager(user, admin);
	}

	/**
	 * Provides a BCryptPasswordEncoder bean for encoding passwords. BCrypt is a
	 * strong hashing algorithm recommended for password storage.
	 *
	 * @return A BCryptPasswordEncoder instance.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}