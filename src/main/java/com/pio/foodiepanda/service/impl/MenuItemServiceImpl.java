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

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void addMenuItem(MenuItemDTO menuItemDTO, Principal principal) {
        String email = principal.getName();
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByOwnerEmail(email);

        if(restaurantOptional.isEmpty()){
            throw new ResourceNotFoundException("Restaurant not found with the username" + email);
        }

        Restaurant restaurant = restaurantOptional.get();
        Categories categories = categoriesRepository.findById(menuItemDTO.getCategory())
                .orElseThrow(()-> new ResourceNotFoundException("Category not found"));

        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemDTO.getName());
        menuItem.setDescription(menuItemDTO.getDescription());
        menuItem.setPrice(menuItemDTO.getPrice());
        menuItem.setAvailable(menuItemDTO.isAvailable());
        menuItem.setVeg(menuItemDTO.isVeg());
        menuItem.setCategories(categories);
        menuItem.setImage(menuItemDTO.getImage()); // Save Base64 string directlyn
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);
    }

    @Override
    public List<MenuItemDTO> getMenuItemsForOwner(String ownerEmail) {
        Restaurant restaurant = restaurantRepository.findByOwnerEmail(ownerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        List<MenuItem> menuItems= menuItemRepository.findByRestaurant(restaurant);
        return menuItems.stream()
                .map( menuItem -> {
                    MenuItemDTO dto = new MenuItemDTO();
                    dto.setId(menuItem.getMenuItemId());
                    dto.setName(menuItem.getName());
                    dto.setDescription(menuItem.getDescription());
                    dto.setPrice(menuItem.getPrice());
                    dto.setCategoryName(menuItem.getCategories().getName());
                    dto.setAvailable(menuItem.isAvailable());
                    dto.setVeg(menuItem.isVeg());
                    dto.setImage(menuItem.getImage());
                    return dto;
                }).collect(Collectors.toList());
    }


}
