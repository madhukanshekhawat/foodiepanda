package com.pio.foodiepanda.service;

public interface RestaurantOwnerService {

    boolean authenticate(String email, String password);

    Long getRestaurantIdByEmail(String email);
}
