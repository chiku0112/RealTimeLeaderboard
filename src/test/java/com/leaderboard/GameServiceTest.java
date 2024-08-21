package com.leaderboard;


import com.leaderboard.enums.UserType;
import com.leaderboard.service.GameService;
import com.leaderboard.service.UserService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

public class GameServiceTest {
    private GameService gameService;
    private String scoreFilePath = "src/test/resources/scores_test.txt";

    @Before
    public void setUp() {
        gameService = new GameService(scoreFilePath);
    }

    @Test
    public void testPublishScore() throws IOException {
        gameService.publishScore(1, 100, 10);
        BufferedReader reader = new BufferedReader(new FileReader(scoreFilePath));
        String line = reader.readLine();
        assertNotNull(line);
        assertEquals("1,100", line);
        reader.close();
    }

    @Test
    public void testPublishScoreWithNewUser() {
        UserService userService = UserService.getInstance();
        userService.registerUser(2, "Bob", UserType.PLAYER);
        gameService.publishScore(3, 200, 10); // New user
        assertTrue(userService.userExists(3));
    }

    @Test
    public void testFileWritingErrorHandling() {
        // Simulate a file writing error by using a non-writable path
        GameService faultyGameService = new GameService("/invalid/path/scores.txt");
        faultyGameService.publishScore(1, 100, 10); // Should handle the exception
        // No assertion here, just checking if it runs without exception
    }
}
