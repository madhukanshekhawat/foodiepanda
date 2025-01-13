package com.pio.foodiepanda.serviceimpl;


import com.pio.foodiepanda.dto.RestaurantDTO;
import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.repository.RestaurantRepository;
import com.pio.foodiepanda.service.RestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<RestaurantDTO> getAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(restaurant -> {
            RestaurantDTO restaurantDTO = new RestaurantDTO();
            restaurantDTO.setName(restaurant.getName());
            restaurantDTO.setPhoneNumber(restaurant.getPhoneNumber());
            restaurantDTO.setOwnerName(restaurant.getRestaurantOwner().getFirstName() + " "
                    + restaurant.getRestaurantOwner().getLastName());
            restaurantDTO.setAddress(restaurant.getAddress().getCity());
            return restaurantDTO;
        }).toList();
    }

}
