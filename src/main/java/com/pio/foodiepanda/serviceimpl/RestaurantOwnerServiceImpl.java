package com.pio.foodiepanda.serviceimpl;

import com.pio.foodiepanda.model.RestaurantOwner;
import com.pio.foodiepanda.repository.RestaurantOwnerRepository;
import com.pio.foodiepanda.service.RestaurantOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantOwnerServiceImpl implements RestaurantOwnerService {

    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;

    @Override
    public List<RestaurantOwner> getAllUnApprovedOwners() {
        return restaurantOwnerRepository.findByIsApprovedFalse();
    }

    @Override
    public RestaurantOwner approveOwner(Long ownerId) {
        RestaurantOwner restaurantOwner = restaurantOwnerRepository.findById(ownerId).orElseThrow(()->new RuntimeException("Owner not found with ID:"+ownerId));
        restaurantOwner.setApproved(true);
        return restaurantOwnerRepository.save(restaurantOwner);
    }
}
