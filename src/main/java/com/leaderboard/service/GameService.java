package com.leaderboard.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GameService {
    private final String scoreFilePath;
    private final UserService userService;

    public GameService(String scoreFilePath) {
        this.scoreFilePath = scoreFilePath;
        this.userService = UserService.getInstance();
    }

    public void publishScore(int playerId, int score, int timeTaken) {
        userService.verifyPlayer(playerId, score, timeTaken);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFilePath, true))) {
            String scoreData = playerId + "," + score + "," + timeTaken + "\n";
            writer.write(scoreData);
        } catch (IOException e) {
            System.out.println("Error writing scores to the file: " + e.getMessage());
        }
    }
}