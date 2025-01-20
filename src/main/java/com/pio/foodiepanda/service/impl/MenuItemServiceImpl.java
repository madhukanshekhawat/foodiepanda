package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.dto.MenuItemDTO;
import com.pio.foodiepanda.exception.ResourceNotFoundException;
import com.pio.foodiepanda.model.Categories;
import com.pio.foodiepanda.model.MenuItem;
import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.repository.CategoriesRepository;
import com.pio.foodiepanda.repository.MenuItemRepository;
import com.pio.foodiepanda.repository.RestaurantRepository;
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
    private CategoriesRepository categoriesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MenuItem addMenuItem(MenuItemDTO menuItemDTO) {
        Restaurant restaurant = restaurantRepository.findById(menuItemDTO.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant Not found"));

        Categories categories = categoriesRepository.findByName(menuItemDTO.getCategoryName())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        MenuItem menuItem = modelMapper.map(menuItemDTO, MenuItem.class);
        menuItem.setRestaurant(restaurant);
        menuItem.setCategories(categories);
        menuItem.setName(menuItemDTO.getName());
        menuItem.setDescription(menuItem.getDescription());
        menuItem.setPrice(menuItem.getPrice());
        menuItem.setAvailable(menuItem.isAvailable());
        menuItem.setVeg(menuItem.isVeg());
        return menuItemRepository.save(menuItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItem> getMenuItemsByRestaurants(Long restaurantId) {
        return menuItemRepository.findByRestaurant_RestaurantId(restaurantId);
    }
}
