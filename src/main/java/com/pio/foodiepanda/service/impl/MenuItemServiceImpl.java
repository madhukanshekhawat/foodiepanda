package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.constants.MessageConstant;
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
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    Logger logger = Logger.getLogger(MenuItemServiceImpl.class.getName());

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private ModelMapper modelMapper;


    /**
     * Adds a new menu item to the restaurant's menu.
     * @param menuItemDTO the data transfer object containing menu item details.
     * @param principal the principal object containing the user's email.
     */
    @Override
    public void addMenuItem(MenuItemDTO menuItemDTO, Principal principal) {
        String email = principal.getName();
        logger.info("Adding menu item for restaurant owner with email:" + email);
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByOwnerEmail(email);

        if (restaurantOptional.isEmpty()) {
            logger.info("Restaurant not found with the username:"+  email);
            throw new ResourceNotFoundException(MessageConstant.RESTAURANT_NOT_FOUND_WITH_USERNAME + email);
        }

        Restaurant restaurant = restaurantOptional.get();
        Categories categories = categoriesRepository.findById(menuItemDTO.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.COUPON_NOT_FOUND));

        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemDTO.getName());
        menuItem.setDescription(menuItemDTO.getDescription());
        menuItem.setPrice(menuItemDTO.getPrice());
        menuItem.setAvailable(true);
        menuItem.setVeg(menuItemDTO.isVeg());
        menuItem.setCategories(categories);
        menuItem.setImage(menuItemDTO.getImage()); // Save Base64 string directlyy
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);
        logger.info("Menu item added successfully for restaurant owner with email:" + email);
    }

    /**
     * Retrieves a list of menu items for a specific restaurant owner.
     * @param ownerEmail the email of the restaurant owner.
     * @return a list of MenuItemDTO objects representing the menu items.
     */
    @Override
    public List<MenuItemDTO> getMenuItemsForOwner(String ownerEmail) {
        logger.info("Fetching menu items for restaurant owner with email:" + ownerEmail);
        Restaurant restaurant = restaurantRepository.findByOwnerEmail(ownerEmail)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.RESTAURANT_NOT_FOUND));

        List<MenuItem> menuItems = menuItemRepository.findByRestaurantAndDeletedFalse(restaurant);
        return menuItems.stream()
                .map(menuItem -> {
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

    /**
     * Updates the availability status of a menu item.
     * @param id the ID of the menu item to be updated.
     * @param available the new availability status.
     * @param ownerEmail the email of the restaurant owner.
     * @return the updated MenuItemDTO object.
     */
    @Override
    public MenuItemDTO updateMenuItemAvailability(Long id, boolean available, String ownerEmail) {
        logger.info("Updating availability for menu item ID:" +id + "for owner email:"+ownerEmail);
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NO_MENU_ITEM_FOUND));

        if (!menuItem.getRestaurant().getRestaurantOwner().getUser().getEmail().equals(ownerEmail)) {
            logger.info("Unauthorized attempt to update menu item availability by email:" + ownerEmail);
            throw new RuntimeException(MessageConstant.UNAUTHORIZED);
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
        logger.info("Menu item availability updated successfully for menu item ID:" + id);
        return dto;
    }

    /**
     * Deletes a menu item by marking it as deleted.
     * @param menuItemId the ID of the menu item to be deleted.
     * @param principal the principal object containing the user's email.
     */
    @Override
    public void deleteMenuItem(Long menuItemId, Principal principal) {
        String email = principal.getName();
        logger.info("Deleting menu item ID:" + menuItemId + "for restaurant owner with email:" + email);

        Restaurant restaurant = restaurantRepository.findByOwnerEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NO_RESTAURANT_OWNER_FOUND));

        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NO_MENU_ITEM_FOUND));

        if (!menuItem.getRestaurant().getRestaurantId().equals(restaurant.getRestaurantId())) {
            logger.info("Unauthorized attempt to delete menu item by email:" + email);
            throw new RuntimeException(MessageConstant.UNAUTHORIZED);
        }

        menuItem.setDeleted(true);
        menuItemRepository.save(menuItem);
        logger.info("Menu item ID:" + menuItemId + "marked as deleted for restaurant owner with email:" + email);
    }

    @Override
    public MenuItemDTO getMenuItemById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(MessageConstant.NO_MENU_ITEM_FOUND));
        return convertToDTO(menuItem);
    }

    @Override
    public String updateMenuItem(Long id, MenuItemDTO menuItemDTO) {
        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(id);
        if(optionalMenuItem.isPresent()){
            MenuItem menuItem = optionalMenuItem.get();

            menuItem.setName(menuItemDTO.getName());
            menuItem.setDescription(menuItemDTO.getDescription());
            menuItem.setPrice(menuItemDTO.getPrice());

            menuItemRepository.save(menuItem);
            return MessageConstant.SUCCESSFUL_MESSAGE;
        }
        else {
            throw new ResourceNotFoundException(MessageConstant.NO_MENU_ITEM_FOUND);
        }
    }

    private MenuItemDTO convertToDTO(MenuItem menuItem){
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(menuItem.getMenuItemId());
        dto.setName(menuItem.getName());
        dto.setDescription(menuItem.getDescription());
        dto.setPrice(menuItem.getPrice());
        return dto;
    }
}
