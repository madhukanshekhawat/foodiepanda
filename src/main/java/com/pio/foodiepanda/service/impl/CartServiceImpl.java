package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.CartItemDTO;
import com.pio.foodiepanda.exception.ResourceNotFoundException;
import com.pio.foodiepanda.model.*;
import com.pio.foodiepanda.repository.*;
import com.pio.foodiepanda.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<CartItemDTO> getCartItemForUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException(MessageConstant.USER_NOT_FOUND);
        }

        Customer customer = user.getCustomer();
        if (customer == null) {
            throw new RuntimeException(MessageConstant.CUSTOMER_NOT_FOUND);
        }

        List<Cart> cartItems = cartRepository.findByCustomer(customer);
        return cartItems.stream()
                .map(cart -> {
                    MenuItem menuItem = menuItemRepository.findById(cart.getMenuItemId())
                            .orElseThrow(() -> new RuntimeException("Menu item not found"));
                    return new CartItemDTO(cart.getCartId(), cart.getMenuItemId(), cart.getQuantity(), cart.getPrice(),  menuItem.getName());
                })
                .collect(Collectors.toList());
    }

    @Override
    public void saveCartItems(List<CartItemDTO> localCart, Principal principal) {
        // Get customer from authenticated user
        String email = principal.getName();
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new RuntimeException("Customer not found for email: " + email);
        }

        // Fetch existing cart items from the database for this customer
        List<Cart> existingCart = cartRepository.findByCustomerId(customer.getCustomerID());

        // Convert local cart items to a map for quick lookup
        Map<Long, Integer> localCartMap = localCart.stream()
                .collect(Collectors.toMap(CartItemDTO::getMenuItemId, CartItemDTO::getQuantity));

        // Remove items from DB that are NOT present in local storage
        existingCart.forEach(existingItem -> {
            Long menuItemId = existingItem.getMenuItemId();
            if (!localCartMap.containsKey(menuItemId)) {
                cartRepository.delete(existingItem);
            }
        });

        // Update quantities for items that exist in both local storage and DB
        existingCart.forEach(existingItem -> {
            Long menuItemId = existingItem.getMenuItemId();
            if (localCartMap.containsKey(menuItemId)) {
                existingItem.setQuantity(localCartMap.get(menuItemId));
                cartRepository.save(existingItem);
            }
        });

        // Add new items from local storage that are not in the database
        for (CartItemDTO localItem : localCart) {
            Long menuItemId = localItem.getMenuItemId();
            Integer quantity = localItem.getQuantity();
            Double price = localItem.getPrice();

            boolean itemExists = existingCart.stream()
                    .anyMatch(item -> item.getMenuItemId().equals(menuItemId));

            if (!itemExists) {
                MenuItem menuItem = menuItemRepository.findById(menuItemId)
                        .orElseThrow(() -> new RuntimeException("MenuItem not found"));

                Cart newCartItem = new Cart();
                newCartItem.setCustomer(customer);
                newCartItem.setMenuItemId(menuItemId);
                newCartItem.setQuantity(quantity);
                newCartItem.setPrice(price); // Set price based on menu item
                newCartItem.setRestaurant(menuItem.getRestaurant()); // Set restaurant
                cartRepository.save(newCartItem);
            }
        }
    }

    @Override
    public boolean removeItemFromCart(Principal principal, Long menuItemId) {
        try {
            String email = principal.getName();
            User user = userRepository.findByEmail(email);
            Long customerId = user.getCustomer().getCustomerID();
            cartRepository.deleteByCustomerIdAndMenuItemId(customerId, menuItemId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Cart convertToEntity(CartItemDTO dto, Customer customer) {

        MenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found"));
        Restaurant restaurant = menuItem.getRestaurant();
        menuItem.setName(dto.getItemName());
        Cart cart = new Cart();
        cart.setMenuItemId(dto.getMenuItemId());
        cart.setQuantity(dto.getQuantity());
        cart.setPrice(dto.getPrice());
        cart.setCustomer(customer);
        cart.setRestaurant(restaurant);
        return cart;
    }
}