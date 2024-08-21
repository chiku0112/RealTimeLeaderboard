package com.leaderboard;
import com.leaderboard.repository.RedisScoreRepository;
import com.leaderboard.models.Score;
import com.leaderboard.service.LeaderboardService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class LeaderboardServiceTest {
    private LeaderboardService leaderboardService;

    @Before
    public void setUp() {
        leaderboardService = LeaderboardService.getInstance("src/test/resources/scores_test.txt",
                new RedisScoreRepository());
        leaderboardService.populateRedis(); // Load initial scores
    }

    @Test
    public void testPopulateRedis() {
        List<Score> topScores = leaderboardService.getTopScores(1);
        assertFalse(topScores.isEmpty());
    }

    @Test
    public void testGetTopScores() {
        List<Score> topScores = leaderboardService.getTopScores(2);
        assertEquals(2, topScores.size());
    }

    @Test
    public void testPublishScoreForNonexistentUser() {
        leaderboardService.publishScore(99, 500); // Nonexistent user should be created
        assertTrue(leaderboardService.getTopScores(1).stream().anyMatch(score -> score.getPlayerId() == 99));
    }
}
