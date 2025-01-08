package com.pio.foodiepanda.controller;
import com.pio.foodiepanda.model.RestaurantOwner;
import com.pio.foodiepanda.service.RestaurantOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestaurantOwnerController {

    @Autowired
    private RestaurantOwnerService restaurantOwnerService;

    @GetMapping("/unapproved-owner")
    public List<RestaurantOwner> getUnapprovedOwners(){
        return restaurantOwnerService.getAllUnApprovedOwners();
    }
}