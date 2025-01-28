package com.pio.foodiepanda.enums;

public enum UserRole {
    ADMIN("Admin"),
    CUSTOMER("Customer"),
    RESTAURANT_OWNER("Restaurant_owner");

    private String type;

    private UserRole(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
