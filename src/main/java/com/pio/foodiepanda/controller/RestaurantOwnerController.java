package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.dto.CouponDTO;
import com.pio.foodiepanda.dto.LoginRequest;
import com.pio.foodiepanda.service.RestaurantOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class RestaurantOwnerController {

        @Autowired
        private RestaurantOwnerService restaurantOwnerService;

        @Autowired
        private AuthenticationManager authenticationManager;

        @PostMapping("/login")
        @ResponseBody
        public ResponseEntity<String> loginRestaurantOwner(@RequestBody LoginRequest loginRequest) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (authentication.isAuthenticated()) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        }
    }