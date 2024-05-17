package com.auth.unit;

import com.auth.dto.UserDto;
import com.auth.model.User;
import com.auth.model.UserRole;
import com.auth.repository.UserRepository;
import com.auth.service.UserService;
import com.auth.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@test.com");
        userDto.setPassword("password");
        userDto.setFirstName("first");
        userDto.setLastName("last");

        User user = new User();
        user.setId(1L);
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRole(UserRole.USER);

        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(userDto);

        assertNotNull(createdUser);
        assertEquals(user.getId(), createdUser.getId());
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals(user.getPassword(), createdUser.getPassword());
        assertEquals(user.getFirstName(), createdUser.getFirstName());
        assertEquals(user.getLastName(), createdUser.getLastName());
        assertEquals(user.getRole(), createdUser.getRole());

        verify(userRepository, times(1)).existsByEmail(userDto.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setEmail("updated@example.com");
        updatedUserDto.setPassword("updatedPassword");
        updatedUserDto.setFirstName("UpdatedFirstName");
        updatedUserDto.setLastName("UpdatedLastName");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setEmail("test@test.com");
        existingUser.setPassword("password");
        existingUser.setFirstName("first");
        existingUser.setLastName("last");
        existingUser.setRole(UserRole.USER);

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setEmail(updatedUserDto.getEmail());
        updatedUser.setPassword(updatedUserDto.getPassword());
        updatedUser.setFirstName(updatedUserDto.getFirstName());
        updatedUser.setLastName(updatedUserDto.getLastName());
        updatedUser.setRole(UserRole.USER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUserDto);

        assertNotNull(result);
        assertEquals(updatedUser.getId(), result.getId());
        assertEquals(updatedUser.getEmail(), result.getEmail());
        assertEquals(updatedUser.getPassword(), result.getPassword());
        assertEquals(updatedUser.getFirstName(), result.getFirstName());
        assertEquals(updatedUser.getLastName(), result.getLastName());
        assertEquals(updatedUser.getRole(), result.getRole());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setEmail("test@test.com");
        existingUser.setPassword("password");
        existingUser.setFirstName("first");
        existingUser.setLastName("last");
        existingUser.setRole(UserRole.USER);

        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }


    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setFirstName("first");
        user.setLastName("last");
        user.setRole(UserRole.USER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User retrievedUser = userService.getUserById(userId);

        assertNotNull(retrievedUser);
        assertEquals(userId, retrievedUser.getId());
        assertEquals("test@test.com", retrievedUser.getEmail());
        assertEquals("first", retrievedUser.getFirstName());
        assertEquals("last", retrievedUser.getLastName());
        assertEquals(UserRole.USER, retrievedUser.getRole());
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "test1@example.com", "password1", UserRole.USER, "first1", "last1"));
        userList.add(new User(2L, "test2@example.com", "password2", UserRole.ADMIN, "first2", "last2"));
        userList.add(new User(3L, "test3@example.com", "password3", UserRole.SUPER_ADMIN, "first3", "last3"));

        when(userRepository.findAll()).thenReturn(userList);

        List<User> retrievedUsers = userService.getAllUsers();

        assertNotNull(retrievedUsers);
        assertEquals(3, retrievedUsers.size());

        assertEquals("test1@example.com", retrievedUsers.get(0).getEmail());
        assertEquals("test2@example.com", retrievedUsers.get(1).getEmail());
        assertEquals("test3@example.com", retrievedUsers.get(2).getEmail());

        assertEquals("first1", retrievedUsers.get(0).getFirstName());
        assertEquals("first2", retrievedUsers.get(1).getFirstName());
        assertEquals("first3", retrievedUsers.get(2).getFirstName());

        assertEquals("last1", retrievedUsers.get(0).getLastName());
        assertEquals("last2", retrievedUsers.get(1).getLastName());
        assertEquals("last3", retrievedUsers.get(2).getLastName());

        assertEquals(UserRole.USER, retrievedUsers.get(0).getRole());
        assertEquals(UserRole.ADMIN, retrievedUsers.get(1).getRole());
        assertEquals(UserRole.SUPER_ADMIN, retrievedUsers.get(2).getRole());
    }
}
