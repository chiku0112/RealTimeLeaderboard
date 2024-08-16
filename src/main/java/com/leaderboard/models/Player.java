package com.leaderboard.models;

import com.leaderboard.enums.UserType;

public class Player extends User{
    public Player(int id, String username, UserType userType) {
        super(id, username, userType);
    }
}
