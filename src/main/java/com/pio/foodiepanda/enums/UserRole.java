package com.pio.foodiepanda.enums;

public enum UserRole {
    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER"),
    RESTAURANT_OWNER("RESTAURANT_OWNER");

    private final String type;

    UserRole(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
