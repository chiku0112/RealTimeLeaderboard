package com.leaderboard.strategy;

import com.leaderboard.models.Score;
import com.leaderboard.service.UserService;
import redis.clients.jedis.resps.Tuple;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByScoreAndTimeStrategy implements ScoreSortingStrategy{
    private final UserService userService = UserService.getInstance();

    @Override
    public List<Score> sort(List<Tuple> scores) {
        return scores.stream()
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
