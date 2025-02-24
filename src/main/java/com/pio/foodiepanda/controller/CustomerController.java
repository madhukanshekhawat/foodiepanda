package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.dto.CustomerDTO;
import com.pio.foodiepanda.dto.RestaurantDTO;
import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.service.CustomerService;
import com.pio.foodiepanda.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllUsers() {
        List<CustomerDTO> customers = customerService.getAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/restaurant/page/{page}/size/{size}")
    public ResponseEntity<Page<RestaurantDTO>> getAllRestaurants(@PathVariable int page, @PathVariable int size) {
        Page<RestaurantDTO> restaurantDTOS = restaurantService.getAllRestaurants(page, size);
        return ResponseEntity.ok(restaurantDTOS);
    }

    @GetMapping("/search-restaurants")
    public List<Restaurant> searchRestaurants(@RequestParam String query) {
        return restaurantService.searchRestaurants(query);
    }

    @GetMapping("/detail/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurantDetails(@PathVariable Long restaurantId) {
        RestaurantDTO restaurant = restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.ok(restaurant);
    }
}
