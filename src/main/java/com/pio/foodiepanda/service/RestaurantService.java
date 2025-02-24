package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.AvailabilityRequest;
import com.pio.foodiepanda.dto.RestaurantDTO;
import com.pio.foodiepanda.model.Restaurant;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface RestaurantService {

    List<RestaurantDTO> getAll();

    RestaurantDTO getRestaurantProfile(Principal principal);

    void updateRestaurantTiming(RestaurantDTO restaurantDTO, Principal principal);

    void updateRestaurantAvailability(AvailabilityRequest availabilityRequest, Principal principal);

    Page<RestaurantDTO> getAllRestaurants(int page, int size);

    RestaurantDTO getRestaurantById(Long restaurantId);

    List<Restaurant> searchRestaurants(String query);
}
