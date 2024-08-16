package com.leaderboard.main;

import com.leaderboard.enums.UserType;
import com.leaderboard.models.Score;
import com.leaderboard.service.ScoreService;
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
        userService.registerUser(4, "kiran", UserType.PLAYER);
        userService.registerUser(5, "mehul", UserType.PLAYER);
        userService.registerUser(6, "lavi", UserType.PLAYER);


        ScoreService scoreService = ScoreService.getInstance();
        scoreService.publishScore(1, 10);
        scoreService.publishScore(2, 20);
        scoreService.publishScore(3, 30);
        scoreService.publishScore(7, 30);
        scoreService.publishScore(8, 30);

        // Simulate concurrent score updates
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                int scored = i*100;
                scoreService.publishScore(i, scored);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();

        // Display leaderboard every 2 seconds
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                List<Score> topScores = scoreService.getTopScores(5);
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
        }, 0, 2000);

        try {
            thread1.join();
            timer.cancel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
