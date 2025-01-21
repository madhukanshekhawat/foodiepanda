package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.dto.MenuItemDTO;
import com.pio.foodiepanda.exception.ResourceNotFoundException;
import com.pio.foodiepanda.model.Categories;
import com.pio.foodiepanda.model.MenuItem;
import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.model.User;
import com.pio.foodiepanda.repository.CategoriesRepository;
import com.pio.foodiepanda.repository.MenuItemRepository;
import com.pio.foodiepanda.repository.RestaurantRepository;
import com.pio.foodiepanda.repository.UserRepository;
import com.pio.foodiepanda.service.MenuItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String addMenuItem(MenuItem menuItem, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
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
