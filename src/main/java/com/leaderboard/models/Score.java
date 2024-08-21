package com.leaderboard.models;

public class Score {
    private int scoreId;
    private int playerId;
    private String playerName;
    private int score;
    private int timeTaken;

    public Score(int scoreId, int playerId, int score, String playerName, int timeTaken) {
        this.scoreId = scoreId;
        this.playerId = playerId;
        this.score = score;
        this.playerName = playerName;
        this.timeTaken = timeTaken;
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

    public int getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getPlayerName() {
        return playerName;
    }
}
