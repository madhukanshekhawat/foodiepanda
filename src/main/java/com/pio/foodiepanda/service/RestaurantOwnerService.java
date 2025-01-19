package com.pio.foodiepanda.service;

import com.pio.foodiepanda.model.RestaurantOwner;
import com.pio.foodiepanda.repository.RestaurantOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface RestaurantOwnerService {

    boolean authenticate(String email, String password);
    Long getRestaurantIdByEmail(String email);
}
