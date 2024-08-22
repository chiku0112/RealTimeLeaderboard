package com.leaderboard.repository;

import com.leaderboard.models.Score;

import java.util.List;

public interface ScoreRepository {
    String addScore(int playerId, int score, int timeTaken);
    List<Score> findTopScores(int limit);
}
