package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.AvailabilityRequest;
import com.pio.foodiepanda.dto.RestaurantDTO;
import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAll() {
        List<RestaurantDTO> restaurants = restaurantService.getAll();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<RestaurantDTO> getRestaurantProfile(Principal principal) {
        RestaurantDTO restaurantProfile = restaurantService.getRestaurantProfile(principal);
        return ResponseEntity.ok(restaurantProfile);
    }

    @PutMapping("/change-availability")
    public ResponseEntity<String> updateRestaurantTiming(@RequestBody RestaurantDTO restaurantDTO, Principal principal) {
        restaurantService.updateRestaurantTiming(restaurantDTO, principal);
        return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
    }

    @PutMapping("/availability")
    public ResponseEntity<String> updateRestaurantAvailability(@RequestBody AvailabilityRequest availabilityRequest, Principal principal) {
        restaurantService.updateRestaurantAvailability(availabilityRequest, principal);
        return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
    }

    @GetMapping("/detail/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurantDetails(@PathVariable Long restaurantId) {
        RestaurantDTO restaurant = restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/search-restaurants")
    public List<Restaurant> searchRestaurants(@RequestParam String query) {
        return restaurantService.searchRestaurants(query);
    }
}
