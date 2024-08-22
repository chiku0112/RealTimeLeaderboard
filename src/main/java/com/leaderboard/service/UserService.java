package com.leaderboard.service;

import com.leaderboard.enums.UserType;
import com.leaderboard.factory.UserFactory;
import com.leaderboard.models.User;

import java.util.HashMap;

public class UserService {
    private static UserService instance;
    private final HashMap<Integer, User> userStore;

    private UserService(){
        userStore = new HashMap<>();
    }

    public static UserService getInstance(){
        if(instance == null){
            instance = new UserService();
        }
        return instance;
    }

    public void registerUser(int id, String username, UserType userType){
        User user = UserFactory.createUser(id, username, userType);
        if(!userStore.containsKey(id)){
            userStore.put(id, user);
        } else {
            System.out.println("User: [" + user.getUsername() + "] already exists");
        }
    }

    public void verifyPlayer(int id, int score, int timeTaken){
        if (!userStore.containsKey(id)) {
            String randomUsername = "player" + id;
            registerUser(id, randomUsername, UserType.PLAYER);
            System.out.println("New Player: [" + randomUsername + "] has joined the game!");
            System.out.println("Scored : "+ score + " in " + timeTaken + " milliseconds");
            System.out.println();
        } else {
            System.out.println("Existing Player: [" + getUserName(id) + "] is already on the game!");
            System.out.println("Scored : "+ score + " in " + timeTaken + " milliseconds");
            System.out.println();
        }
    }

    public Boolean userExists(int id){
        return userStore.containsKey(id);
    }

    public String getUserName(int id){
        if(userStore.containsKey(id)){
            return userStore.get(id).getUsername();
        } else {
            return "player"+id;
        }
    }
}
