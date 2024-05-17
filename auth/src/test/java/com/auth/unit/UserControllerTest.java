package com.auth.unit;

import com.auth.controller.UserController;
import com.auth.dto.UserDto;
import com.auth.model.User;
import com.auth.service.JwtService;
import com.auth.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;

    @Test
    @WithMockUser(username = "tester", authorities = {"SUPER_ADMIN"})
    public void testRegisterUser() throws Exception {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setPassword("password");
        userDto.setEmail("test@example.com");
        // Mock the behavior of UserService to return the created user when createUser method is called with userDto
        when(userService.createUser(any(UserDto.class))).thenReturn(new User());

        // Act & Assert
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetUserById() throws Exception {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("test@test.com");
        // Mock the behavior of UserService to return the user when getUserById method is called with ID 1
        when(userService.getUserById(userId)).thenReturn(user);

        // Act & Assert
        mockMvc.perform(get("/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Arrange
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setEmail("updated@test@com");
        // Mock the behavior of UserService to return the updated user when updateUser method is called with ID 1 and userDto
        when(userService.updateUser(eq(userId), any(UserDto.class))).thenReturn(new User());

        // Act & Assert
        mockMvc.perform(put("/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Arrange
        Long userId = 1L;
        // Mock the behavior of UserService to successfully delete the user when deleteUser method is called with ID 1
        doNothing().when(userService).deleteUser(userId);

        // Act & Assert
        mockMvc.perform(delete("/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Arrange
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("user1@test.com");
        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("user2@test.com");
        userList.add(user1);
        userList.add(user2);
        // Mock the behavior of UserService to return the list of users when getAllUsers method is called
        when(userService.getAllUsers()).thenReturn(userList);

        // Act & Assert
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].username").value("user2"));
    }
}
