package com.auth.service;

import com.auth.dto.RegistrationRequest;
import com.auth.model.User;
import com.auth.model.UserRole;
import com.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegistrationRequest req) {
        if(userRepository.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }
        if(userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email is already taken");
        }

        User user = User.builder()
        .username(req.getUsername())
        .email(req.getEmail())
        .password(passwordEncoder.encode(req.getPassword()))
        .role(UserRole.USER)
        .build();

        userRepository.save(user);
    }
}
