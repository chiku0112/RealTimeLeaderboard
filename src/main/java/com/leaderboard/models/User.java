package com.leaderboard.models;

import com.leaderboard.enums.UserType;

public class User {
    private int id;
    private String username;
    private UserType userType;

    public User(int id, String username, UserType userType) {
        this.id = id;
        this.username = username;
        this.userType = userType;
    }

    public int getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public UserType getUserType(){
        return userType;
    }

}
