package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.model.RestaurantOwner;
import com.pio.foodiepanda.repository.RestaurantOwnerRepository;
import com.pio.foodiepanda.repository.RestaurantRepository;
import com.pio.foodiepanda.service.RestaurantOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantOwnerServiceImpl implements RestaurantOwnerService {

    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public boolean authenticate(String email, String password) {
        RestaurantOwner owner = restaurantOwnerRepository.findByUserEmail(email);
        return owner != null && owner.getUser().getPassword().equals(password);
    }

    @Override
    public Long getRestaurantIdByEmail(String email) {
        RestaurantOwner owner = restaurantOwnerRepository.findByUserEmail(email);
        if (owner != null) {
            Restaurant restaurant = restaurantRepository.findByRestaurantOwner(owner);
            if (restaurant != null) {
                return restaurant.getRestaurantId();
            }
        }
        throw new RuntimeException("Restaurant or restaurant owner not found");
    }
}
