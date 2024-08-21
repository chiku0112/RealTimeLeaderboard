//package com.leaderboard.main;
//
//import com.leaderboard.repository.RedisScoreRepository;
//import com.leaderboard.service.GameService;
//import com.leaderboard.service.LeaderboardService;
//
//import java.util.*;
//
//public class Main {
//    public static void main(String[] args) {
//
//        String scoreFilePath = "src/main/resources/scores.txt";
//        GameService gameService = new GameService(scoreFilePath);
//        LeaderboardService leaderboardService = LeaderboardService.getInstance(scoreFilePath, new RedisScoreRepository());
//
//        Scanner scanner = new Scanner(System.in);
//        boolean running = true;
//
//        while (running) {
//            System.out.println("-----------------------------------");
//            System.out.println("Choose an option:");
//            System.out.println("1. Initiate a game (publish random scores)");
//            System.out.println("2. Display top K players");
//            System.out.println("3. Exit");
//            System.out.println("-----------------------------------");
//
//            int choice = scanner.nextInt();
//            switch (choice) {
//                case 1:
//                    System.out.println("Initiating game...");
//                    System.out.println("Game will run for 10 seconds...");
//                    System.out.println("-----------------------------------");
//                    System.out.println("-----------------------------------");
//
//                    Thread thread = new Thread(() -> {
//                        for (int i = 1; i <= 5; i++) {
//                            int playerId = generateRandomPlayerId();
//                            int scored = i * 10;
//                            int timeTaken = new Random().nextInt(100);
//                            gameService.publishScore(playerId, scored, timeTaken);
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                System.out.println(e.getMessage());
//                            }
//                        }
//                    });
//                    thread.start();
//                    try {
//                        thread.join();
//                    } catch (InterruptedException e) {
//                        System.out.println(e.getMessage());
//                    }
//                    System.out.println("-----------------------------------");
//
//                    System.out.println("Game has ended.");
//                    System.out.println("Updating the leaderboard...");
//                    System.out.println("-----------------------------------");
//                    leaderboardService.populateRedis();
//                    leaderboardService.displayLeaderboard(5);
//                    break;
//
//                case 2:
//                    System.out.println("Enter the value of K for top K players:");
//                    int k = scanner.nextInt();
//                    leaderboardService.displayLeaderboard(k);
//                    break;
//
//                case 3:
//                    running = false;
//                    break;
//
//                default:
//                    System.out.println("Invalid option. Please try again.");
//            }
//        }
//        scanner.close();
//    }
//
//    // Implement a method to generate random player IDs
//    private static int generateRandomPlayerId() {
//        Random random = new Random();
//        return random.nextInt(10) + 1;
//    }
//}