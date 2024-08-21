package com.leaderboard.service;

import com.leaderboard.models.Score;
import com.leaderboard.repository.RedisScoreRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LeaderboardService {
    private static LeaderboardService instance;
    private RedisScoreRepository scoreRepository;
    private UserService userService;
    private String scoreFilePath;;

    private LeaderboardService(String scoreFilePath, RedisScoreRepository scoreRepository) {
        this.scoreRepository = new RedisScoreRepository();
        this.userService = UserService.getInstance();
        this.scoreFilePath = scoreFilePath;
    }

    public static LeaderboardService getInstance(String scoreFilePath, RedisScoreRepository scoreRepository) {
        if (instance == null) {
            instance = new LeaderboardService(scoreFilePath, scoreRepository);
        }
        return instance;
    }

    public void readScoresFromFileAndPublishToRedis() {
        try (Scanner scanner = new Scanner(new File(scoreFilePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int playerId = Integer.parseInt(parts[0]);
                int score = Integer.parseInt(parts[1]);
                publishScore(playerId, score);
            }
        } catch (IOException e) {
            System.out.println("Error reading scores from file: " + e.getMessage());
        }
    }

    public void populateRedis() {
        readScoresFromFileAndPublishToRedis();
    }

    public void publishScore(int playerId, int score) {
        userService.verifyPlayer(playerId);
        scoreRepository.addScore(playerId, score);
    }

    public List<Score> getTopScores(int k) {
        return scoreRepository.findTopScores(k);
    }

    public void displayLeaderboard(int k) {
        List<Score> topScores = getTopScores(k);
        System.out.println("-----------------------------------");
        System.out.println("-----------------------------------");
        System.out.println("++++++++++ | LEADERBOARD | ++++++++");
        for (Score score : topScores) {
            String playerName = userService.getUserName(score.getPlayerId());
            System.out.println("PlayerID: " + score.getPlayerId() + ", PlayerName: " + playerName + ", Score: " + score.getScore());
        }
        System.out.println("-----------------------------------");
        System.out.println("-----------------------------------");
    }
}