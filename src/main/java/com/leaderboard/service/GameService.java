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

    public void publishScore(int playerId, int score, int timeTaken) {
        if (!userService.userExists(playerId)) {
            String randomUsername = "player" + playerId;
            userService.registerUser(playerId, randomUsername, UserType.PLAYER);
            System.out.println("New Player: [" + randomUsername + "] has joined the game!");
            System.out.println("Scored : "+ score + " in " + timeTaken + " milliseconds");
            System.out.println();
        } else {
            System.out.println("Existing Player: [" + userService.getUserName(playerId) + "] is already on the game!");
            System.out.println("Scored : "+ score + " in " + timeTaken + " milliseconds");
            System.out.println();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFilePath, true))) {
            String scoreData = playerId + "," + score + "," + timeTaken + "\n";
            writer.write(scoreData);
        } catch (IOException e) {
            System.out.println("Error writing scores to the file: " + e.getMessage());
        }
    }
}