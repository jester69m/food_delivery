package com.auth.controller;

import com.auth.dto.*;
import com.auth.model.User;
import com.auth.service.AuthService;
import com.auth.service.JwtService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authenticationService;
    private final UserDetailsService userDetailsService;
    private final RabbitTemplate rabbitTemplate;

    private String registrationQueueName = "q.user-registration";
    private String loginQueueName = "q.user-login";
    private String exchangeName = "user.exchange";


    public AuthController(JwtService jwtService, AuthService authenticationService, UserDetailsService userDetailsService, RabbitTemplate rabbitTemplate) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        try{
            authenticationService.registerUser(registerRequest);
            rabbitTemplate.convertAndSend(exchangeName, registrationQueueName, new QueueUserResponse(registerRequest.getEmail(), registerRequest.getFirstName(), registerRequest.getLastName()));
            return ResponseEntity.ok("User created successfully");
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody LoginRequest loginUserDto) {
        try{
            User authenticatedUser = authenticationService.authenticate(loginUserDto);
            String jwtToken = jwtService.generateToken(authenticatedUser);
            JwtResponse loginResponse = new JwtResponse(authenticatedUser.getEmail(), jwtToken, jwtService.getExpirationTime());
            rabbitTemplate.convertAndSend(exchangeName, loginQueueName, authenticatedUser.getEmail());
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/verify-token/{jwtToken}")
    public ResponseEntity<?> checkJwtToken(@PathVariable String jwtToken) {
        try{
            String email = jwtService.extractUsername(jwtToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if(jwtService.isTokenValid(jwtToken, userDetails)) {
                return ResponseEntity.ok(userDetails);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
