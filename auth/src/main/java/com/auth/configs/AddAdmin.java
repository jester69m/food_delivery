package com.auth.configs;

import com.auth.model.User;
import com.auth.model.UserRole;
import com.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddAdmin implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${security.super-admin.email}")
    private String superAdminEmail;
    @Value("${security.super-admin.password}")
    private String superAdminPassword;

    @Override
    public void run(String... args) throws Exception {
        userRepository.findByEmail(superAdminEmail).ifPresentOrElse(
                user -> {
                    System.out.println("Super admin already exists");
                },
                () -> {
                    User user = new User();
                    user.setEmail(superAdminEmail);
                    user.setPassword(passwordEncoder.encode(superAdminPassword));
                    user.setRole(UserRole.SUPER_ADMIN);
                    userRepository.save(user);
                    System.out.println("Super admin created");
                }
        );
    }


}
