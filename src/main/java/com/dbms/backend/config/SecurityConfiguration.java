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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    "/health"
  };
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .cors()
          .and()
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
}
