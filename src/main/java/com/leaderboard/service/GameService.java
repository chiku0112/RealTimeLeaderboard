package com.leaderboard.service;

import com.leaderboard.enums.UserType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GameService {
    private String scoreFilePath;
    private UserService userService;

    public GameService(String scoreFilePath) {
        this.scoreFilePath = scoreFilePath;
        this.userService = UserService.getInstance();
    }

    public void publishScore(int playerId, int score) {
        if (!userService.userExists(playerId)) {
            String randomUsername = "player" + playerId;
            userService.registerUser(playerId, randomUsername, UserType.PLAYER);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFilePath, true))) {
            String scoreData = playerId + "," + score + "\n";
            writer.write(scoreData);
        } catch (IOException e) {
            System.out.println("Error writing scores to the file: " + e.getMessage());
        }
    }
}