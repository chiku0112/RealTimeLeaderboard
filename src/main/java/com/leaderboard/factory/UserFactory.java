package com.leaderboard.factory;

import com.leaderboard.enums.UserType;
import com.leaderboard.models.Admin;
import com.leaderboard.models.Player;
import com.leaderboard.models.User;

public class UserFactory {
    public static User createUser(int id, String userName, UserType userType) {
        switch (userType) {
            case ADMIN:
                return new Admin(id, userName, userType);
            case PLAYER:
                return new Player(id, userName, userType);
            // Additional cases for new user types...
            default:
                throw new IllegalArgumentException("Unknown user type: " + userType);
        }
    }
}
