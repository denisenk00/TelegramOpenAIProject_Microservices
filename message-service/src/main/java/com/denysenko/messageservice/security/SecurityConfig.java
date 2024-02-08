package com.denysenko.messageservice.security;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final String [] WHITELIST = {"/private/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(CsrfConfigurer::disable)
                .cors(CorsConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .logout(LogoutConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers(WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                .build();
    }


}
