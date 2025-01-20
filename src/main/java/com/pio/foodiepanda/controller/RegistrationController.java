package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.constants.ViewConstant;
import com.pio.foodiepanda.dto.RestaurantRegisterDTO;
import com.pio.foodiepanda.dto.UserDTO;
import com.pio.foodiepanda.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> registerRestaurant(@RequestBody RestaurantRegisterDTO restaurantRegisterDTO) {
        try {
            userService.registerRestaurant(restaurantRegisterDTO);
            return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageConstant.UNEXPECTED_ERROR + e.getMessage());
        }
    }
}
