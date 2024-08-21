package com.leaderboard;

import com.leaderboard.enums.UserType;
import com.leaderboard.service.UserService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserServiceTest {
    private UserService userService;

    @Before
    public void setUp() {
        userService = UserService.getInstance();
        userService.registerUser(1, "Alice", UserType.PLAYER);
    }

    @Test
    public void testUserRegistration() {
        userService.registerUser(2, "Bob", UserType.PLAYER);
        assertEquals("Bob", userService.getUserName(2));
    }

    @Test
    public void testDuplicateUserRegistration() {
        userService.registerUser(1, "Charlie", UserType.PLAYER); // Attempt to register duplicate
        assertEquals("Alice", userService.getUserName(1)); // Should still be "Alice"
    }

    @Test
    public void testUserExists() {
        assertTrue(userService.userExists(1));
        assertFalse(userService.userExists(3)); // User not registered
    }
}
