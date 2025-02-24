package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.AvailabilityRequest;
import com.pio.foodiepanda.dto.OrdersDTO;
import com.pio.foodiepanda.dto.RestaurantDTO;
import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.service.OrderService;
import com.pio.foodiepanda.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private OrderService orderService;

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

    @GetMapping("/order/all")
    public ResponseEntity<List<OrdersDTO>> getOrder(Principal principal) {
        String email = principal.getName();
        List<OrdersDTO> orders = orderService.getOrdersForRestaurant(email);
        return ResponseEntity.ok(orders);
    }

}
