package com.auth.config;

import com.auth.model.User;
import com.auth.model.UserRole;
import com.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddTestUsers implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(!userRepository.existsByUsername("testuser") && !userRepository.existsByUsername("testadmin")) {
            // Add test user
            userRepository.save(User.builder()
                    .username("testuser")
                    .email("testuser@example.com")
                    .password(passwordEncoder.encode("123"))
                    .role(UserRole.USER)
                    .firstName("Test")
                    .lastName("User")
                    .createdAt("2022-02-15")
                    .build());

            // Add test admin
            userRepository.save(User.builder()
                    .username("testadmin")
                    .email("testadmin@example.com")
                    .password(passwordEncoder.encode("123"))
                    .role(UserRole.ADMIN)
                    .firstName("Test")
                    .lastName("Admin")
                    .createdAt("2022-02-15")
                    .build());
        }
    }


}
