package com.leaderboard.strategy;

import com.leaderboard.models.Score;
import com.leaderboard.service.UserService;
import redis.clients.jedis.resps.Tuple;

import java.util.List;

public interface ScoreSortingStrategy {
    List<Score> sort(List<Tuple> scores);
}
