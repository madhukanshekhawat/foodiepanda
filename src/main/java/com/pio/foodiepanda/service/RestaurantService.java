package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.RestaurantDTO;

import java.util.List;

public interface RestaurantService {

    List<RestaurantDTO> getAll();

//    RestaurantDTO approveRestaurant(Long restaurantId, boolean approved);

}
