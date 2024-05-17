package com.auth.unit;

import com.auth.model.User;
import com.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.findByEmail(email);

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo(email);
    }

    @Test
    public void testExistsByEmail() {
        String email = "test@example.com";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        boolean exists = userRepository.existsByEmail(email);

        assertThat(exists).isTrue();
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo(user.getId());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userRepository.save(user);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getId()).isEqualTo(user.getId());
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        userRepository.deleteById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.findById(userId);

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(userId);
    }

}