package com.leaderboard.repository;

import com.leaderboard.models.Score;
import com.leaderboard.strategy.ScoreSortingStrategy;
import com.leaderboard.strategy.SortByScoreAndTimeStrategy;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.resps.Tuple;

import java.util.List;

public class RedisScoreRepository implements ScoreRepository {
    private Jedis jedis;

    public RedisScoreRepository() {
        this.jedis = new Jedis("localhost", 6379);
    }

    @Override
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

    @Override
    public List<Score> findTopScores(int limit) {
        // Retrieve the top scores using Redis sorted set
        List<Tuple> playersWithScores = jedis.zrevrangeWithScores("scores", 0, limit - 1);
        ScoreSortingStrategy sortingStrategy = new SortByScoreAndTimeStrategy();
        return sortingStrategy.sort(playersWithScores);
    }
}

