package com.leaderboard;

import com.leaderboard.models.Score;
import com.leaderboard.repository.RedisScoreRepository;
import com.leaderboard.service.LeaderboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

public class LeaderboardServiceTest {
    private LeaderboardService leaderboardService;
    private RedisScoreRepository mockRedisRepository;

    @BeforeEach
    void setUp() {
        mockRedisRepository = Mockito.mock(RedisScoreRepository.class);
        leaderboardService = LeaderboardService.getInstance("src/test/resources/test_scores.txt", mockRedisRepository);
    }

    @Test
    public void testPopulateRedis() {
        leaderboardService.populateRedis();
        verify(mockRedisRepository, Mockito.atMostOnce()).addScore(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void testGetTopScores() {
        leaderboardService.populateRedis();
        List<Score> topScores = leaderboardService.getTopScores(3);
        assertNotNull(topScores);
        assertEquals(3, topScores.size());
    }
}