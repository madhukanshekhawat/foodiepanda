package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.AvailabilityRequest;
import com.pio.foodiepanda.dto.RestaurantDTO;

import java.security.Principal;
import java.util.List;

public interface RestaurantService {

    List<RestaurantDTO> getAll();


    RestaurantDTO getRestaurantProfile(Principal principal);

    void updateRestaurantTiming(RestaurantDTO restaurantDTO, Principal principal);

    void updateRestaurantAvailability(AvailabilityRequest availabilityRequest, Principal principal);
}
