package com.leaderboard.models;

public class Score {
    private int scoreId;
    private int playerId;
    private int score;
    private String timestamp;

    public Score(int scoreId, int playerId, int score, String timestamp) {
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
