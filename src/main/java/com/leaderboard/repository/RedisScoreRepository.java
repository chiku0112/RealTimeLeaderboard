package com.leaderboard.repository;

import com.leaderboard.models.Score;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;
import java.util.stream.Collectors;

public class RedisScoreRepository {
    private Jedis jedis;

    public RedisScoreRepository() {
        this.jedis = new Jedis("localhost", 6379);
    }

    public void addScore(int playerId, int score) {
        String member = String.valueOf(playerId);
        try {
            jedis.zadd("scores", score, member);
            System.out.println("Added score for player " + member);
        } catch (JedisConnectionException e) {
            System.out.println("Error adding score for player " + member + ": " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error adding score: " + e.getMessage());
        }
    }

    public List<Score> findTopScores(int limit) {
        // Retrieve the top scores using Redis sorted set
        List<String> playerIds = jedis.zrevrange("scores", 0, limit - 1);

        return playerIds.stream()
                .map(playerId -> {
                    Double score = jedis.zscore("scores", playerId);
                    return new Score(0, Integer.parseInt(playerId), score.intValue(), "");
                })
                .collect(Collectors.toList());
    }
}

