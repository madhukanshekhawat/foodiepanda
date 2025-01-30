package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.dto.CustomerDTO;
import com.pio.foodiepanda.dto.RestaurantDTO;
import com.pio.foodiepanda.service.CustomerService;
import com.pio.foodiepanda.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
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
    public ResponseEntity<Page<RestaurantDTO>> getAllRestaurants(@PathVariable int page, @PathVariable int size){
        Page<RestaurantDTO> restaurantDTOS = restaurantService.getAllRestaurants(page,size);
        return ResponseEntity.ok(restaurantDTOS);
    }
}
