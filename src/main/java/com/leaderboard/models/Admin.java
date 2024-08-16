package com.leaderboard.models;

import com.leaderboard.enums.UserType;

public class Admin extends User {
    public Admin(int id, String username, UserType userType) {
        super(id, username, userType);
    }
}
