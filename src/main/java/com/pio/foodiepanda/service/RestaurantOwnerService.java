package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.RestaurantDTO;
import com.pio.foodiepanda.model.RestaurantOwner;

import java.util.List;

public interface RestaurantOwnerService {
    List<RestaurantOwner> getAllUnApprovedOwners();
    RestaurantOwner approveOwner(Long ownerId);


}
