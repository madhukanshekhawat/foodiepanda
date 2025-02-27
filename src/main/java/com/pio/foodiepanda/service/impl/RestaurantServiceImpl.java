package com.pio.foodiepanda.service.impl;


import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.AvailabilityRequest;
import com.pio.foodiepanda.dto.MenuItemDTO;
import com.pio.foodiepanda.dto.RestaurantDTO;
import com.pio.foodiepanda.exception.ResourceNotFoundException;
import com.pio.foodiepanda.model.MenuItem;
import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.model.RestaurantOwner;
import com.pio.foodiepanda.repository.MenuItemRepository;
import com.pio.foodiepanda.repository.RestaurantRepository;
import com.pio.foodiepanda.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
public class RestaurantServiceImpl implements RestaurantService {

    Logger logger = Logger.getLogger(RestaurantServiceImpl.class.getName());
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;

    private static List<MenuItemDTO> getMenuItemDTOS(List<MenuItem> menuItems) {
        List<MenuItemDTO> menuItemDTOs = new ArrayList<>();
        for (MenuItem item : menuItems) {
            MenuItemDTO menuItemDTO = new MenuItemDTO();
            menuItemDTO.setId(item.getMenuItemId());
            menuItemDTO.setImage(item.getImage());
            menuItemDTO.setDescription(item.getDescription());
            menuItemDTO.setVeg(item.isVeg());
            menuItemDTO.setCategoryName(item.getCategories().getName());
            menuItemDTO.setName(item.getName());
            menuItemDTO.setPrice(item.getPrice());
            menuItemDTO.setAvailable(item.isAvailable());
            menuItemDTO.setDeleted(item.isDeleted());
            menuItemDTOs.add(menuItemDTO);
        }
        return menuItemDTOs;
    }

    /*
     * Retrieves a list of all restaurants from db
     * @return : A list of RestaurantDTO obj representing all restaurants
     */
    @Override
    public List<RestaurantDTO> getAll() {
        List<Restaurant> restaurants = restaurantRepository.findAllByOwnerApproved();
        return restaurants.stream().map(restaurant -> {
            RestaurantDTO restaurantDTO = new RestaurantDTO();
            restaurantDTO.setRestaurantId(restaurant.getRestaurantId());
            restaurantDTO.setName(restaurant.getName());
            restaurantDTO.setPhoneNumber(restaurant.getPhoneNumber());
            restaurantDTO.setAddress(restaurant.getRestaurantAddress().getAddressLine() + " ," + restaurant.getRestaurantAddress().getCity() + " ," + restaurant.getRestaurantAddress().getState() + " ," + restaurant.getRestaurantAddress().getPostalCode());
            RestaurantDTO.OwnerDetails ownerDetails = new RestaurantDTO.OwnerDetails();
            RestaurantOwner restaurantOwner = restaurant.getRestaurantOwner();
            if (restaurantOwner != null) {
                ownerDetails.setFirstName(restaurantOwner.getFirstName());
                ownerDetails.setLastName(restaurantOwner.getLastName());
            }
            restaurantDTO.setOwnerDetails(ownerDetails);
            return restaurantDTO;
        }).toList();
    }

    /**
     * Retrieves the profile of a restaurant based on the principal's email.
     *
     * @param principal the principal object containing the user's email.
     * @return a RestaurantDTO object representing the restaurant profile.
     */
    @Override
    public RestaurantDTO getRestaurantProfile(Principal principal) {
        String email = principal.getName();
        logger.info(MessageConstant.FETCHING_RESTAURANT_PROFILE + email);
        Restaurant restaurant = restaurantRepository.findByOwnerEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.RESTAURANT_NOT_FOUND));

        RestaurantDTO dto = new RestaurantDTO();
        dto.setName(restaurant.getName());
        dto.setAvailabilityStartTime(restaurant.getStartTime());
        dto.setAvailabilityEndTime(restaurant.getEndTime());
        dto.setAvailable(restaurant.isAvailable());

        if (restaurant.getRestaurantAddress() != null) {
            dto.setAddress(restaurant.getRestaurantAddress().getAddressLine() + " " + restaurant.getRestaurantAddress().getCity() + " " + restaurant.getRestaurantAddress().getState() + " " + restaurant.getRestaurantAddress().getPostalCode());
        }
        return dto;
    }

    /**
     * Updates the timing of a restaurant based on the provided RestaurantDTO and principal's email.
     *
     * @param restaurantDTO the data transfer object containing the new timing details.
     * @param principal     the principal object containing the user's email.
     */
    @Override
    public void updateRestaurantTiming(RestaurantDTO restaurantDTO, Principal principal) {
        String email = principal.getName();
        logger.info(MessageConstant.UPDATING_RESTAURANT_TIMING + email);
        Restaurant restaurant = restaurantRepository.findByOwnerEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        restaurant.setStartTime(restaurantDTO.getAvailabilityStartTime());
        restaurant.setEndTime(restaurantDTO.getAvailabilityEndTime());

        restaurantRepository.save(restaurant);
        logger.info(MessageConstant.SUCCESSFUL_MESSAGE + email);
    }

    /**
     * Updates the availability status of a restaurant based on the provided AvailabilityRequest and principal's email.
     *
     * @param availabilityRequest the request object containing the new availability status.
     * @param principal           the principal object containing the user's email.
     */
    @Override
    public void updateRestaurantAvailability(AvailabilityRequest availabilityRequest, Principal principal) {
        String email = principal.getName();
        logger.info(MessageConstant.UPDATING_RESTAURANT_AVAILABILITY + email);
        Restaurant restaurant = restaurantRepository.findByOwnerEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.RESTAURANT_NOT_FOUND));

        restaurant.setAvailable(availabilityRequest.isAvailable());
        restaurantRepository.save(restaurant);
        logger.info(MessageConstant.SUCCESSFUL_MESSAGE + email);
    }

    /**
     * Retrieves a paginated list of all restaurants.
     *
     * @param page the page number to retrieve.
     * @param size the number of records per page.
     * @return a paginated list of RestaurantDTO objects.
     */
    @Override
    public Page<RestaurantDTO> getAllRestaurants(int page, int size) {
        logger.info(MessageConstant.FETCHING_RESTAURANT + page + MessageConstant.SIZE_INDICATION + size);
        Pageable pageable = PageRequest.of(page, size);

        Page<Restaurant> restaurantPage = restaurantRepository.findAllRestaurantByOwnerApproved(pageable);

        return restaurantPage.map(restaurant -> {
            RestaurantDTO dto = new RestaurantDTO();
            dto.setRestaurantId(restaurant.getRestaurantId());
            dto.setName(restaurant.getName());
            dto.setAddress(restaurant.getRestaurantAddress().getCity());
            dto.setAvailable(restaurant.isAvailable());

            MenuItem menuItem = menuItemRepository.findFirstByRestaurant_RestaurantId(restaurant.getRestaurantId());
            if (menuItem != null) {
                dto.setImage(menuItem.getImage());
            }
            return dto;
        });
    }

    /**
     * Retrieves a restaurant by its ID.
     *
     * @param restaurantId the ID of the restaurant to retrieve.
     * @return the RestaurantDTO object containing the restaurant information.
     * @throws ResourceNotFoundException if the restaurant is not found.
     */
    @Override
    public RestaurantDTO getRestaurantById(Long restaurantId) {
        logger.info(MessageConstant.FETCHING_RESTAURANT + restaurantId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.RESTAURANT_NOT_FOUND));

        List<MenuItem> menuItems = menuItemRepository.findByRestaurantId(restaurantId);

        List<MenuItemDTO> menuItemDTOs = getMenuItemDTOS(menuItems);

        // Create and populate RestaurantDTO
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setRestaurantId(restaurant.getRestaurantId());
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setAddress(restaurant.getRestaurantAddress().getCity());
        restaurantDTO.setOwnerId(restaurant.getRestaurantOwner().getOwnerID());
        restaurantDTO.setAvailabilityStartTime(restaurant.getStartTime());
        restaurantDTO.setAvailabilityEndTime(restaurant.getEndTime());
        restaurantDTO.setAvailable(restaurant.isAvailable());
        restaurantDTO.setPhoneNumber(restaurant.getRestaurantOwner().getPhoneNumber());
        restaurantDTO.setMenuItems(menuItemDTOs);

        logger.info(MessageConstant.SUCCESSFUL_MESSAGE + restaurantId);
        return restaurantDTO;
    }

    /**
     * Searches for restaurants by a query string.
     *
     * @param query the query string to search for.
     * @return a list of Restaurant objects that match the query.
     */
    @Override
    public List<Restaurant> searchRestaurants(String query) {
        logger.info(MessageConstant.SEARCH_FOR_RESTAURANT + query);
        List<Restaurant> restaurantsByName = restaurantRepository.findByNameContainingIgnoreCase(query);
        List<MenuItem> menuItemsByName = menuItemRepository.findByNameContainingIgnoreCase(query);

        List<Restaurant> restaurantsByMenuItem = menuItemsByName.stream()
                .map(MenuItem::getRestaurant)
                .distinct()
                .collect(Collectors.toList());

        restaurantsByName.addAll(restaurantsByMenuItem);
        List<Restaurant> distinctRestaurants = restaurantsByName.stream().distinct().collect(Collectors.toList());

        // Add the first menu item image to each restaurant
        distinctRestaurants.forEach(restaurant -> {
            List<MenuItem> menuItems = menuItemRepository.findByRestaurant(restaurant);
            if (!menuItems.isEmpty()) {
                restaurant.setFirstMenuItemImage(menuItems.get(0).getImage());
            }
        });

        logger.info(MessageConstant.SEARCH_RESULT_FETCH_SUCCESSFULLY_FOR_QUERY + query);
        return distinctRestaurants;
    }

    @Override
    public RestaurantDTO getRestaurantAvailability(Principal principal) {
        String email = principal.getName();

        Restaurant restaurant = restaurantRepository.getRestaurantByEmail(email);

        // Create a new RestaurantDTO object
        RestaurantDTO restaurantDTO = new RestaurantDTO();

        // Set the availability status
        restaurantDTO.setAvailable(restaurant.isAvailable());

        return restaurantDTO;
    }
}