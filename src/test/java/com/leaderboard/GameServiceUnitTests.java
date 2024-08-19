package com.leaderboard;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.leaderboard.service.GameService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameServiceUnitTests {
    private static final String TEST_FILE = "src/test/resources/test_scores.txt";

    @Test
    public void testPublishScore() throws IOException {
        GameService gameService = new GameService(TEST_FILE);
        gameService.publishScore(123, 999);

        assertTrue(Files.exists(Path.of(TEST_FILE)));

        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE))) {
            String line = reader.readLine();
            assertEquals("123,999", line);
        }
    }
}