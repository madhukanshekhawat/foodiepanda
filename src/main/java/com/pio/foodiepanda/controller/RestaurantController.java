package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.AvailabilityRequest;
import com.pio.foodiepanda.dto.OrderStatusDTO;
import com.pio.foodiepanda.dto.OrdersDTO;
import com.pio.foodiepanda.dto.RestaurantDTO;
import com.pio.foodiepanda.enums.OrderStatus;
import com.pio.foodiepanda.service.OrderService;
import com.pio.foodiepanda.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/availability")
    public ResponseEntity<RestaurantDTO> getRestaurantAvailability(Principal principal){
        RestaurantDTO restaurantAvailability = restaurantService.getRestaurantAvailability(principal);
        return ResponseEntity.ok(restaurantAvailability);
    }
    @GetMapping("/order/all")
    public ResponseEntity<List<OrdersDTO>> getOrder(Principal principal) {
        String email = principal.getName();
        List<OrdersDTO> orders = orderService.getOrdersForRestaurant(email);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatusDTO orderStatusDTO) {
        orderService.updateOrderStatus(orderId, OrderStatus.valueOf(orderStatusDTO.getStatus()));
        return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
    }

}
