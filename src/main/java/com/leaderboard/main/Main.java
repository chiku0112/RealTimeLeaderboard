package com.leaderboard.main;

import com.leaderboard.enums.UserType;
import com.leaderboard.models.Score;
import com.leaderboard.service.GameService;
import com.leaderboard.service.LeaderboardService;
import com.leaderboard.service.UserService;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        UserService userService = UserService.getInstance();

        userService.registerUser(1, "ayushi", UserType.ADMIN);
        userService.registerUser(2, "rishabh", UserType.PLAYER);
        userService.registerUser(3, "vito", UserType.PLAYER);

        String scoreFilePath = "src/main/resources/scores.txt";
        GameService gameService = new GameService(scoreFilePath);
        LeaderboardService leaderboardService = LeaderboardService.getInstance(scoreFilePath);

        // Simulate game play
        Thread thread1 = new Thread(() -> {
            for (int i = 1; i <=10; i++) {
                int scored = i*100;
                gameService.publishScore(i, scored);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        thread1.start();

        // Display leaderboard every 2 seconds
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                leaderboardService.populateRedis();
                List<Score> topScores = leaderboardService.getTopScores(5);
                System.out.println();
                System.out.println("-----------------------------------");
                System.out.println("Leaderboard: ");
                for (Score score : topScores) {
                    System.out.println("PlayerID: " + score.getPlayerId() +
                            ", PlayerName: " +
                            ", Score: " + score.getScore());
                }
                System.out.println("-----------------------------------");
                System.out.println();
            }
        }, 0, 1000);

        try {
            thread1.join();
            timer.cancel();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
