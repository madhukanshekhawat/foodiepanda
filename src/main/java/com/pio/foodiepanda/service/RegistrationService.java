package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.RestaurantDTO;
import com.pio.foodiepanda.dto.UserDTO;

public interface RegistrationService {
    Long registerUser(UserDTO userDTO) throws Exception;

    void registerRestaurant(RestaurantDTO restaurantDTO) throws Exception;
}
