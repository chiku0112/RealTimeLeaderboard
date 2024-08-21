package com.leaderboard.strategy;

import com.leaderboard.models.Score;
import com.leaderboard.service.UserService;
import redis.clients.jedis.resps.Tuple;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByTimeStrategy implements ScoreSortingStrategy {
    private UserService userService = UserService.getInstance();

    @Override
    public List<Score> sort(List<Tuple> tuples) {
        return tuples.stream()
                .map(tuple -> {
                    String[] parts = tuple.getElement().split(":");
                    int playerId = Integer.parseInt(parts[0]);
                    int timeTaken = Integer.parseInt(parts[1]);
                    // Create a Score object from the tuple
                    return new Score(0, playerId, (int) tuple.getScore(), userService.getUserName(playerId), timeTaken);
                })
                .sorted(Comparator.comparingInt(Score::getTimeTaken)) // Sort by time taken in ascending order
                .collect(Collectors.toList());
    }
}
