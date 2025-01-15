package com.pio.foodiepanda.service.impl;


import com.pio.foodiepanda.dto.RestaurantDTO;
import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.repository.RestaurantRepository;
import com.pio.foodiepanda.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    /*
     * Retrieves a list of all restaurants from db
     * @return : A list of RestaurantDTO obj representing all restaurants
     */
    @Override
    public List<RestaurantDTO> getAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(restaurant -> {
            RestaurantDTO restaurantDTO = new RestaurantDTO();
            restaurantDTO.setName(restaurant.getName());
            restaurantDTO.setPhoneNumber(restaurant.getPhoneNumber());
            restaurantDTO.setOwnerName(restaurant.getRestaurantOwner().getFirstName() + " "
                    + restaurant.getRestaurantOwner().getLastName());
            restaurantDTO.setAddress(restaurant.getAddress().getAddressLine()+ " ," +restaurant.getAddress().getCity()+ " ,"+ restaurant.getAddress().getState() + " ,"+ restaurant.getAddress().getPostalCode());
            return restaurantDTO;
        }).toList();
    }

}
