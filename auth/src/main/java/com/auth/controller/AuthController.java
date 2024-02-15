package com.auth.controller;

import com.auth.dto.RegistrationRequest;
import com.auth.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    UserServiceImpl userService;


    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello, World!");
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        userService.registerUser(registrationRequest);
        return ResponseEntity.ok("User registered successfully");
    }


}
