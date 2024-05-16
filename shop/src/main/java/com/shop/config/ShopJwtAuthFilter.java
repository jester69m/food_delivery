package com.shop.config;

import com.shop.dto.JwtRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ShopJwtAuthFilter extends OncePerRequestFilter {


    @Value("${auth.url}")
    private String authServiceUrl;

    private final RestTemplate restTemplate;

    public ShopJwtAuthFilter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            ResponseEntity<UserDetails> userDetailsResponse = restTemplate.exchange(
                    authServiceUrl + "/auth/verify-token",
                    HttpMethod.GET,
                    new HttpEntity<>(new JwtRequest(jwt)),
                    UserDetails.class);

            if (userDetailsResponse.getStatusCode().is2xxSuccessful()) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetailsResponse.getBody(), null, userDetailsResponse.getBody().getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception ex) {
            System.out.println("Error authenticating user: " + ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private UserDetails verifyJwtToken(String jwtToken) {
        return restTemplate.getForObject(authServiceUrl + "/"+jwtToken, UserDetails.class);

    }
}