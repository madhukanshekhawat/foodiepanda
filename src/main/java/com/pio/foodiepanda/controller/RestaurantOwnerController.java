package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.dto.RestaurantOwnerDTO;
import com.pio.foodiepanda.service.RestaurantOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class RestaurantOwnerController {

    @Autowired
    private RestaurantOwnerService restaurantOwnerService;

    @GetMapping("/unapproved-owner")
    public List<RestaurantOwnerDTO> getUnapprovedOwners() {
        return restaurantOwnerService.getUnApprovedOwners();
    }

    @GetMapping("/approved-owner")
    public ResponseEntity<List<RestaurantOwnerDTO>> getApprovedOwners() {
        List<RestaurantOwnerDTO> approvedOwners = restaurantOwnerService.getApprovedOwners();
        return ResponseEntity.ok(approvedOwners);
    }

    @PostMapping("{restaurantOwnerId}/{action}")
    public ResponseEntity<?> handleOwnerAction(@PathVariable Long restaurantOwnerId, @PathVariable String action) {
        if ("approve".equalsIgnoreCase(action)) {
            restaurantOwnerService.approveOwner(restaurantOwnerId);
            return ResponseEntity.ok().build();
        } else if ("delete".equalsIgnoreCase(action)) {
            restaurantOwnerService.deleteOwner(restaurantOwnerId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Invalid action");
    }
}