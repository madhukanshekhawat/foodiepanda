package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.RestaurantRegisterDTO;
import com.pio.foodiepanda.dto.UserDTO;

public interface RegistrationService {
    Long registerUser(UserDTO userDTO) throws Exception;

    void registerRestaurant(RestaurantRegisterDTO restaurantRegisterDTO) throws Exception;
}
