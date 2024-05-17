package com.shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final ShopJwtAuthFilter shopJwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/product/**").permitAll()
                        .requestMatchers("/product/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/shop/**").permitAll()
                        .requestMatchers("/shop/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/order/**").authenticated()
                        .requestMatchers("/order/**").hasAnyAuthority("ADMIN", "SUPER_ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(shopJwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
