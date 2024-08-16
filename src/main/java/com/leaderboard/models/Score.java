package com.leaderboard.models;

public class Score {
    private int scoreId;
    private int playerId;
    private Double score;
    private String timestamp;

    // Constructors, getters, and setters
    public Score(int scoreId, int playerId, Double score, String timestamp) {
        this.scoreId = scoreId;
        this.playerId = playerId;
        this.score = score;
        this.timestamp = timestamp;
    }

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
