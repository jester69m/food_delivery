package com.auth.unit;

import com.auth.controller.AuthController;
import com.auth.dto.LoginRequest;
import com.auth.dto.RegisterRequest;
import com.auth.model.User;
import com.auth.service.AuthService;
import com.auth.service.JwtService;
import com.auth.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testRegister() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");
        registerRequest.setFirstName("first");
        registerRequest.setLastName("second");

        doNothing().when(authService).registerUser(any(RegisterRequest.class));
        doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString(), Optional.ofNullable(any()));

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("User created successfully"));
    }

    @Test
    public void testAuthenticate() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");

        when(authService.authenticate(any(LoginRequest.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.jwtToken").value("jwtToken"));
    }
}
