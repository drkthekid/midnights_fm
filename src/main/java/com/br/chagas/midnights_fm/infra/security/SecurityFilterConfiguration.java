package com.br.chagas.midnights_fm.infra.security;

import com.br.chagas.midnights_fm.exception.CustomAcessDeniedException;
import com.br.chagas.midnights_fm.handler.CustomSecurityAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterConfiguration {

    private final SecurityFilter securityFilter;
    private final CustomSecurityAccessDeniedHandler customSecurityAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                // transforms application in STATELESS (for each request -> needs a token)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                        // swagger
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // public endpoint
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/refresh").permitAll()

                        // user endpoint
                        .requestMatchers("/api/users/**").authenticated()

                        // track endpoint
                        .requestMatchers(HttpMethod.POST, "/api/tracks/**").hasRole("ARTIST")
                        .requestMatchers(HttpMethod.PUT, "/api/tracks/**").hasRole("ARTIST")
                        .requestMatchers(HttpMethod.DELETE, "/api/tracks/**").hasRole("ARTIST")
                        .requestMatchers(HttpMethod.GET, "/api/tracks/**").authenticated()

                        // album endpoint
                        .requestMatchers(HttpMethod.POST, "/api/albums/**").hasRole("ARTIST")
                        .requestMatchers(HttpMethod.PUT, "/api/albums/**").hasRole("ARTIST")
                        .requestMatchers(HttpMethod.DELETE, "/api/albums/**").hasRole("ARTIST")
                        .requestMatchers(HttpMethod.GET, "/api/albums/**").authenticated()

                        // obsession endpoint
                        .requestMatchers("/api/obsessions/**").authenticated()

                        // review endpoint
                        .requestMatchers("/api/reviews/**").authenticated()

                        // playlist endpoint
                        .requestMatchers("/api/playlists/**").authenticated()

                        // invite endpoint
                        .requestMatchers("/api/invites/**").authenticated()

                        .anyRequest().authenticated())
                .exceptionHandling(handling -> handling.accessDeniedHandler(customSecurityAccessDeniedHandler))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // authentication in login, valid username/password
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // hash password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
