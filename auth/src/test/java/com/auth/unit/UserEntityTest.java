package com.auth.unit;

import com.auth.model.User;
import com.auth.model.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserEntityTest {
    @Test
    public void testUserEntity() {
        // Create a user
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole(UserRole.USER);
        user.setFirstName("John");
        user.setLastName("Doe");

        // Test getters
        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(UserRole.USER, user.getRole());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());

        // Test equals and hashcode
        User sameUser = new User();
        sameUser.setId(1L);
        sameUser.setEmail("test@example.com");
        sameUser.setPassword("password");
        sameUser.setRole(UserRole.USER);
        sameUser.setFirstName("John");
        sameUser.setLastName("Doe");
        assertEquals(user, sameUser);
        assertEquals(user.hashCode(), sameUser.hashCode());

        // Test toString
        assertNotNull(user.toString());
    }
}
