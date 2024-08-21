package com.leaderboard;

import com.leaderboard.models.Score;
import com.leaderboard.repository.RedisScoreRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RedisScoreRepositoryTest {
    private RedisScoreRepository scoreRepository;

    @Before
    public void setUp() {
        scoreRepository = new RedisScoreRepository();
        scoreRepository.addScore(1, 100, 50); // Add a score to test with
    }

    @Test
    public void testAddScore() {
        scoreRepository.addScore(2, 200, 50);
        assertEquals(200, scoreRepository.findTopScores(1).get(0).getScore());
    }

    @Test
    public void testFindTopScores() {
        scoreRepository.addScore(3, 300, 50);
        List<Score> topScores = scoreRepository.findTopScores(2);
        assertEquals(2, topScores.size());
        assertEquals(300, topScores.get(0).getScore());
    }
}
