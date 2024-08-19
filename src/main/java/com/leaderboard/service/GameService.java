package com.leaderboard.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GameService {
    private String scoreFilePath;

    public GameService(String scoreFilePath) {
        this.scoreFilePath = scoreFilePath;
    }

    public void publishScore(int playerId, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFilePath, true))) {
            String scoreData = playerId + "," + score + "\n";
            writer.write(scoreData);
        } catch (IOException e) {
            System.out.println("Error writing scores to the file: " + e.getMessage());
        }
    }
}