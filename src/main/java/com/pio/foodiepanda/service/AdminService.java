package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.RestaurantOwnerDTO;

import java.util.List;

public interface AdminService {

    List<RestaurantOwnerDTO> getUnApprovedOwners();

    void approveOwner(Long restaurantOwnerId);

    void rejectOwner(Long restaurantOwnerId);

    List<RestaurantOwnerDTO> getApprovedOwners();
}