package org.demo.eventtracker.API.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Auth
                        .requestMatchers("/auth/login").permitAll()

                        // Public GET endpoints
                        .requestMatchers(HttpMethod.GET, "/about/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/events/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/rooms/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/speakers/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/sessions/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/questions/**").permitAll()

                        // Public participant interactions
                        .requestMatchers(HttpMethod.POST, "/sessions/*/questions").permitAll()
                        .requestMatchers(HttpMethod.POST, "/questions/*/upvote").permitAll()

                        // Admin protected CRUD
                        .requestMatchers(HttpMethod.POST, "/events").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/events/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/events/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/rooms").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/rooms/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/rooms/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/speakers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/speakers/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/speakers/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/sessions").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/sessions/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/sessions/*").hasRole("ADMIN")

                        // Admin protected speaker assignment
                        .requestMatchers(HttpMethod.POST, "/sessions/*/speakers/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/sessions/*/speakers/*").hasRole("ADMIN")

                        // Default
                        .anyRequest().permitAll()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:5173"
        ));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}