package com.auth.service;

import com.auth.model.User;
import com.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> found = userRepository.findByUsername(usernameOrEmail);
        if (found.isEmpty()) {
            found = userRepository.findByEmail(usernameOrEmail);
        }
        return found.map(user -> {
            if (user.isEnabled()) {
                return user;
            } else {
                throw new DisabledException("User account is disabled");
            }
        }).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail)
        );
    }


}
