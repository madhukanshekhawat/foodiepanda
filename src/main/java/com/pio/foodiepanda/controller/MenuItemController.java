package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.dto.MenuItemDTO;
import com.pio.foodiepanda.model.MenuItem;
import com.pio.foodiepanda.service.MenuItemService;
import com.pio.foodiepanda.service.RestaurantOwnerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owner/menu")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private RestaurantOwnerService restaurantOwnerService;

    @PostMapping("/add")
    public ResponseEntity<String> addMenuItem(@RequestBody MenuItemDTO menuItemDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Get the logged-in user's email

        Long restaurantId = restaurantOwnerService.getRestaurantIdByEmail(email);
        menuItemDTO.setRestaurantId(restaurantId);
        menuItemService.addMenuItem(menuItemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Menu item added successfully");
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<MenuItem>> getMenuItem(@PathVariable Long restaurantId){
        List<MenuItem> menuItems = menuItemService.getMenuItemsByRestaurants(restaurantId);
        return ResponseEntity.ok(menuItems);
    }
}
