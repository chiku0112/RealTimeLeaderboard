package com.leaderboard.service;

import com.leaderboard.enums.UserType;
import com.leaderboard.factory.UserFactory;
import com.leaderboard.models.User;

import java.util.HashMap;

public class UserService {
    private static UserService instance;
    private HashMap<Integer, User> userStore;

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

    public String getUserName(int id){
        if(userStore.containsKey(id)){
            return userStore.get(id).getUsername();
        } else {
            return "player"+id;
        }
    }

    public void verifyPlayer(int id){
        if(!userStore.containsKey(id)){
            User user = UserFactory.createUser(id, "nn", UserType.PLAYER);
            userStore.put(id, user);
        }
    }

    public Boolean userExists(int id){
        return userStore.containsKey(id);
    }
}
