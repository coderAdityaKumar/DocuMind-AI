package com.aditya.documind.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.aditya.documind.Security.JwtFilter;

@Configuration
public class SecurityConfig {
     private final JwtFilter jwtFilter;

     public SecurityConfig(JwtFilter jwtFilter){
        this.jwtFilter=jwtFilter;
     }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // ✅ Public endpoints
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**","/actuator/**","/error").permitAll()

                // 🔒 Everything else secured
                .anyRequest().authenticated()
            )

            // 🔥 Add JWT filter BEFORE Spring security
            .addFilterBefore(jwtFilter,
                    org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    
}
