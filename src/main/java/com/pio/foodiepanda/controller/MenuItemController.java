package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.model.MenuItem;
import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.model.User;
import com.pio.foodiepanda.repository.MenuItemRepository;
import com.pio.foodiepanda.repository.RestaurantRepository;
import com.pio.foodiepanda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuItemController {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public String addMenuItem(@RequestBody MenuItem menuItem, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        Restaurant restaurant = restaurantRepository.findByRestaurantOwner(user.getRestaurantOwner());

        if (restaurant != null) {
            menuItem.setRestaurant(restaurant);
            menuItemRepository.save(menuItem);
            return "Menu item added successfully!";
        } else {
            return "Restaurant not found!";
        }
    }
}