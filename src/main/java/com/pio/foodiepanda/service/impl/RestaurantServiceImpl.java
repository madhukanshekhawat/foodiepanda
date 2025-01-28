package com.pio.foodiepanda.service.impl;


import com.pio.foodiepanda.dto.AvailabilityRequest;
import com.pio.foodiepanda.dto.RestaurantDTO;
import com.pio.foodiepanda.exception.ResourceNotFoundException;
import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.model.RestaurantAddress;
import com.pio.foodiepanda.model.RestaurantOwner;
import com.pio.foodiepanda.repository.RestaurantRepository;
import com.pio.foodiepanda.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
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
            restaurantDTO.setRestaurantId(restaurant.getRestaurantId());
            restaurantDTO.setName(restaurant.getName());
            restaurantDTO.setPhoneNumber(restaurant.getPhoneNumber());
            restaurantDTO.setAddress(restaurant.getRestaurantAddress().getAddressLine() + " ," + restaurant.getRestaurantAddress().getCity() + " ," + restaurant.getRestaurantAddress().getState() + " ," + restaurant.getRestaurantAddress().getPostalCode());
            RestaurantDTO.OwnerDetails ownerDetails = new RestaurantDTO.OwnerDetails();
            RestaurantOwner restaurantOwner = restaurant.getRestaurantOwner();
            if (restaurantOwner != null) {
                ownerDetails.setFirstName(restaurantOwner.getFirstName());
                ownerDetails.setLastName(restaurantOwner.getLastName());
            }
            restaurantDTO.setOwnerDetails(ownerDetails);
            return restaurantDTO;
        }).toList();
    }

    @Override
    public RestaurantDTO getRestaurantProfile(Principal principal) {
        String email = principal.getName();
        Restaurant restaurant = restaurantRepository.findByOwnerEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("Restaurant not found"));

        RestaurantDTO dto = new RestaurantDTO();
        dto.setName(restaurant.getName());
        dto.setAvailabilityStartTime(restaurant.getStartTime());
        dto.setAvailabilityEndTime(restaurant.getEndTime());
        dto.isAvailable(restaurant.isAvailable());

        if(restaurant.getRestaurantAddress() != null){
            dto.setAddress(restaurant.getRestaurantAddress().getAddressLine()+" "+ restaurant.getRestaurantAddress().getCity()+" "+restaurant.getRestaurantAddress().getCity()+" "+restaurant.getRestaurantAddress().getPostalCode());
        }
        return dto;
    }

    @Override
    public void updateRestaurantTiming(RestaurantDTO restaurantDTO, Principal principal) {
        String email = principal.getName();
        Restaurant restaurant = restaurantRepository.findByOwnerEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("Restaurant not found"));

        restaurant.setStartTime(restaurantDTO.getAvailabilityStartTime());
        restaurant.setEndTime(restaurantDTO.getAvailabilityEndTime());

        restaurantRepository.save(restaurant);
    }

    @Override
    public void updateRestaurantAvailability(AvailabilityRequest availabilityRequest, Principal principal) {
        String email = principal.getName();
        Restaurant restaurant = restaurantRepository.findByOwnerEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("Restaurant not found"));

        restaurant.setAvailable(availabilityRequest.isAvailable());
        restaurantRepository.save(restaurant);
    }

    @Override
    public Page<RestaurantDTO> getAllRestaurants(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);

        Page<Restaurant> restaurantPage = restaurantRepository.findAll(pageable);

        return restaurantPage.map(restaurant -> {
            RestaurantDTO dto = new RestaurantDTO();
            dto.setName(restaurant.getName());
            dto.setAddress(restaurant.getRestaurantAddress().getCity());
            dto.isAvailable(restaurant.isAvailable());
            return dto;
        });
    }

}
