package com.shop.config;

import com.shop.dto.UserDetailsResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ShopJwtAuthFilter extends OncePerRequestFilter {


    @Value("${auth.url}")
    private String authUrl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String jwtToken = getJwtFromRequest(request);

        if (StringUtils.hasText(jwtToken)) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<UserDetailsResponse> responseEntity = restTemplate.getForEntity(authUrl + jwtToken, UserDetailsResponse.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                UserDetailsResponse userDetailsResponse = responseEntity.getBody();
                if (userDetailsResponse != null) {
                    UserDetails userDetails = User.builder().username(userDetailsResponse.getEmail()).password("").roles(userDetailsResponse.getRole()).build();

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}