package com.epam.auction.entity;

import java.util.Arrays;

public enum UserRole {

    ADMIN(1),
    USER(2);

    private int id;

    UserRole(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static UserRole define(int id) {
        return Arrays.stream(UserRole.values())
                .filter(userRole -> id == userRole.id)
                .findFirst()
                .orElse(null);
    }

}