package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.model.RestaurantOwner;
import com.pio.foodiepanda.repository.RestaurantOwnerRepository;
import com.pio.foodiepanda.repository.RestaurantRepository;
import com.pio.foodiepanda.service.RestaurantOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class RestaurantOwnerServiceImpl implements RestaurantOwnerService {

    Logger logger = Logger.getLogger(RestaurantServiceImpl.class.getName());

    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * Authenticate a restaurant owner using the provided email and password
     *
     * @param email    : the email of the restaurant owner
     * @param password : the password of the restaurant owner
     * @return true if authentication is successful, false otherwise
     */
    public boolean authenticate(String email, String password) {
        logger.info(MessageConstant.AUTHENTICATING_USER + email);
        RestaurantOwner owner = restaurantOwnerRepository.findByUserEmail(email);
        return owner != null && owner.getUser().getPassword().equals(password);
    }

    /**
     * Retrieves the restaurant ID associated with the given restaurant owner's email
     *
     * @param email : the email of the restaurant
     * @return the restaurant id if found
     * @throws RuntimeException if the restaurant owner is not found
     */
    @Override
    public Long getRestaurantIdByEmail(String email) {
        logger.info(MessageConstant.GETTING_USER + email);
        RestaurantOwner owner = restaurantOwnerRepository.findByUserEmail(email);
        if (owner != null) {
            Restaurant restaurant = restaurantRepository.findByRestaurantOwner(owner);
            if (restaurant != null) {
                return restaurant.getRestaurantId();
            }
        }
        throw new RuntimeException(MessageConstant.RESTAURANT_OR_OWNER_NOT_FOUND);
    }
}
