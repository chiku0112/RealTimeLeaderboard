package com.leaderboard.controller;
import com.leaderboard.models.Score;
import com.leaderboard.repository.RedisScoreRepository;
import com.leaderboard.service.LeaderboardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LeaderboardController {
    private final LeaderboardService leaderboardService;

    public LeaderboardController() {
        this.leaderboardService = LeaderboardService.getInstance("src/main/resources/scores.txt",
                new RedisScoreRepository());
        System.out.println("LeaderboardController loaded");
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "Test endpoint is working!";
    }

    @PostMapping("/scores")
    public String postScore(@RequestParam int playerId, @RequestParam int score, @RequestParam int timeTaken) {
        return leaderboardService.publishScore(playerId, score, timeTaken);
    }

    @GetMapping("/scores/{k}")
    public List<Score> getTopKScores(@PathVariable int k) {
        return leaderboardService.getTopScores(k);
    }


}
