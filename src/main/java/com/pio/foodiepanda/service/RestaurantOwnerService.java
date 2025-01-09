package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.RestaurantOwnerDTO;

import java.util.List;

public interface RestaurantOwnerService {

    List<RestaurantOwnerDTO> getUnApprovedOwners();

    void approveOwner(Long restaurantOwnerId);

    void deleteOwner(Long restaurantOwnerId);

    List<RestaurantOwnerDTO> getApprovedOwners();
}