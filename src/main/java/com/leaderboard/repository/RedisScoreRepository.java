package com.leaderboard.repository;

import com.leaderboard.models.Score;
import com.leaderboard.service.UserService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.resps.Tuple;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RedisScoreRepository {
    private Jedis jedis;
    private UserService userService;

    public RedisScoreRepository() {
        this.jedis = new Jedis("localhost", 6379);
        userService = UserService.getInstance();
    }

    public String addScore(int playerId, int score, int timeTaken) {
        String member = playerId + ":" + timeTaken;
        Double existingScore = jedis.zscore("scores", member);

        if (existingScore == null || score > existingScore) {
            try {
                jedis.zadd("scores", score, member);
            } catch (JedisConnectionException e) {
                System.out.println("Error adding score for player " + member + ": " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error adding score: " + e.getMessage());
            }
        } else {
            return "Score not updated as the existing score is higher or equal.";
        }
        return "Score added successfully";
    }

    public List<Score> findTopScores(int limit) {
        // Retrieve the top scores using Redis sorted set
        List<Tuple> playersWithScores = jedis.zrevrangeWithScores("scores", 0, limit - 1);

        return playersWithScores.stream()
                .map(tuple -> {
                    String[] parts = tuple.getElement().split(":");
                    int playerId = Integer.parseInt(parts[0]);
                    int timeTaken = Integer.parseInt(parts[1]);
                    return new Score(0, playerId, (int) tuple.getScore(), userService.getUserName(playerId), timeTaken);
                })
                .sorted(Comparator.comparingInt(Score::getScore).reversed()  // Sort by score in descending order
                        .thenComparingInt(Score::getTimeTaken))  // Then sort by time taken in ascending order
                .collect(Collectors.toList());
    }
}

