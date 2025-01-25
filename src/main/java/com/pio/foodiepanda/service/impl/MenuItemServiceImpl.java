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

        List<MenuItem> menuItems= menuItemRepository.findByRestaurantAndDeletedFalse(restaurant);
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

    @Override
    public MenuItemDTO updateMenuItemAvailability(Long id, boolean available, String ownerEmail) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Menu item not found"));

        if(!menuItem.getRestaurant().getRestaurantOwner().getUser().getEmail().equals(ownerEmail)){
            throw new RuntimeException("Unauthorized");
        }

        menuItem.setAvailable(available);
        MenuItem updatedMenuItem = menuItemRepository.save(menuItem);

        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(updatedMenuItem.getMenuItemId());
        dto.setName(updatedMenuItem.getName());
        dto.setDescription(updatedMenuItem.getDescription());
        dto.setPrice(updatedMenuItem.getPrice());
        dto.setCategoryName(updatedMenuItem.getCategories().getName());
        dto.setAvailable(updatedMenuItem.isAvailable());
        dto.setVeg(updatedMenuItem.isVeg());
        dto.setImage(updatedMenuItem.getImage());
        return dto;
    }

    @Override
    public void deleteMenuItem(Long menuItemId, Principal principal) {
        String email = principal.getName();

        Restaurant restaurant = restaurantRepository.findByOwnerEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("No Restaurant owner found"));

        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(()-> new ResourceNotFoundException("Menu Item not found"));

        if(!menuItem.getRestaurant().getRestaurantId().equals(restaurant.getRestaurantId())){
            throw new RuntimeException("Unauthorized");
        }

        menuItem.setDeleted(true);
        menuItemRepository.save(menuItem);
    }

}
