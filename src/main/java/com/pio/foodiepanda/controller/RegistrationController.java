package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.dto.RestaurantDTO;
import com.pio.foodiepanda.dto.UserDTO;
import com.pio.foodiepanda.exception.InvalidUserDataException;
import com.pio.foodiepanda.exception.UserAlreadyExistsException;
import com.pio.foodiepanda.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private RegistrationService userService;

    @PostMapping("/registerUser")
    public ResponseEntity<Long> registerUser(@RequestBody UserDTO userDTO) {
        try {
            Long ownerId = userService.registerUser(userDTO);
            return ResponseEntity.ok(ownerId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/restaurant")
    public ResponseEntity<String> registerRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        try {
            userService.registerRestaurant(restaurantDTO);
            return ResponseEntity.ok("Restaurant details saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
