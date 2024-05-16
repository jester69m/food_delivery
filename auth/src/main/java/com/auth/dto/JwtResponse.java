package com.auth.dto;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String email;
    private String jwtToken;
    private long expirationTime;
}
