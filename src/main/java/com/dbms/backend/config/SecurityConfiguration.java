package com.dbms.backend.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.util.List;

import com.dbms.backend.models.user.Role;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
    private static final String[] WHITE_LIST_URL = {
    "/auth/authenticate",
    "/health",
    // "/auth/register"
  };
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .cors(cors -> cors.configurationSource(corsConfigurationSource()))
          .csrf(AbstractHttpConfigurer::disable)
          .authorizeHttpRequests(req ->{
                    req.requestMatchers(WHITE_LIST_URL)
                        .permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/").authenticated()
                        .anyRequest().hasAuthority("USER");
             
            }
          )
          .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
          .authenticationProvider(authenticationProvider)
          .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // ALLOW ALL ORIGINS
        configuration.setAllowedOrigins(List.of("*")); 
        // OR use specific list: configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:3000"));

        // ALLOW ALL METHODS (GET, POST, PUT, DELETE, OPTIONS)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // ALLOW ALL HEADERS (Authorization, Content-Type, etc.)
        configuration.setAllowedHeaders(List.of("*"));

        // Register this config for all routes
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
