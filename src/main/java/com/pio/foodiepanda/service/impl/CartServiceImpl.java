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
import java.util.List;
import java.util.Map;
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

        List<Cart> cartItems = cartRepository.findByCustomerId(customer.getCustomerID());
        return cartItems.stream()
                .map(cart -> {
                    MenuItem menuItem = menuItemRepository.findById(cart.getMenuItemId())
                            .orElseThrow(() -> new RuntimeException("Menu item not found"));
                    return new CartItemDTO(cart.getCartId(), cart.getMenuItemId(), cart.getQuantity(), cart.getPrice(),  menuItem.getName(),customer.getCustomerID(),cart.getRestaurant().getRestaurantId());
                })
                .collect(Collectors.toList());
    }

    @Override
    public void saveCartItems(Map<Long, Integer> localCart, Principal principal) {
        String email = principal.getName();
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new RuntimeException("Customer not found for email: " + email);
        }

        List<Cart> existingCart = cartRepository.findByCustomerId(customer.getCustomerID());

        // Remove items from the database that are not in the local cart
        existingCart.forEach(existingItem -> {
            Long menuItemId = existingItem.getMenuItemId();
            if (!localCart.containsKey(menuItemId)) {
                cartRepository.delete(existingItem);
            }
        });

        // Update quantities for existing items and add new items
        localCart.forEach((menuItemId, quantity) -> {
            Cart existingItem = existingCart.stream()
                    .filter(item -> item.getMenuItemId().equals(menuItemId))
                    .findFirst()
                    .orElse(null);

            if (existingItem != null) {
                existingItem.setQuantity(quantity);
                cartRepository.save(existingItem);
            } else {
                MenuItem menuItem = menuItemRepository.findById(menuItemId)
                        .orElseThrow(() -> new RuntimeException("MenuItem not found"));

                Cart newCartItem = new Cart();
                newCartItem.setCustomer(customer);
                newCartItem.setMenuItemId(menuItemId);
                newCartItem.setQuantity(quantity);
                newCartItem.setPrice(menuItem.getPrice());
                newCartItem.setRestaurant(menuItem.getRestaurant());
                cartRepository.save(newCartItem);
            }
        });
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

    @Override
    public void clearCartForUser(Principal principal) {
        String email = principal.getName();

        Customer customer = customerRepository.findByEmail(email);
        if(customer == null){
            throw new ResourceNotFoundException(MessageConstant.USER_NOT_FOUND);
        }
        cartRepository.deleteByCustomerId(customer.getCustomerID());
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