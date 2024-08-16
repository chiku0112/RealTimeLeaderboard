package com.leaderboard.service;

import com.leaderboard.models.Score;
import com.leaderboard.repository.RedisScoreRepository;

import java.util.List;

public class ScoreService {
    private static ScoreService instance;
    private RedisScoreRepository scoreRepository;
    private UserService userService;

    private ScoreService() {
        scoreRepository = new RedisScoreRepository();
        userService = UserService.getInstance();
    }

    public static ScoreService getInstance() {
        if (instance == null) {
            instance = new ScoreService();
        }
        return instance;
    }

    public void  publishScore(int playerId, int score) {
        userService.verifyPlayer(playerId);
        scoreRepository.addScore(playerId, score);
    }

    public List<Score> getTopScores(int k) {
        return scoreRepository.findTopScores(k);
    }
}