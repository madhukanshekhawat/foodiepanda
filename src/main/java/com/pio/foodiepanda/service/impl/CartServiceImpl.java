package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.CartItemDTO;
import com.pio.foodiepanda.exception.ResourceNotFoundException;
import com.pio.foodiepanda.model.*;
import com.pio.foodiepanda.repository.CartRepository;
import com.pio.foodiepanda.repository.CustomerRepository;
import com.pio.foodiepanda.repository.MenuItemRepository;
import com.pio.foodiepanda.repository.UserRepository;
import com.pio.foodiepanda.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
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

    @Override
    public List<CartItemDTO> getCartItemForUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException(MessageConstant.USER_NOT_FOUND);
        }

        Customer customer = customerRepository.findByUser(user.getId());
        if (customer == null) {
            throw new RuntimeException(MessageConstant.CUSTOMER_NOT_FOUND);
        }

        List<Cart> cartItems = cartRepository.findByCustomer(customer);
        return cartItems.stream()
                .map(cart -> {
                    return new CartItemDTO(cart.getCartId(), cart.getMenuItemId(), cart.getQuantity(), cart.getPrice());
                })
                .collect(Collectors.toList());
    }

    @Override
    public void saveCartItems(List<CartItemDTO> cartItemDTO, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        if (user == null) {
            throw new ResourceNotFoundException(MessageConstant.USER_NOT_FOUND);
        }
        Customer customer = user.getCustomer();

        for (CartItemDTO dto : cartItemDTO) {
            Cart cartItem = convertToEntity(dto, customer);
            cartRepository.save(cartItem);
        }
    }

    private Cart convertToEntity(CartItemDTO dto, Customer customer) {

        MenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found"));
        Restaurant restaurant = menuItem.getRestaurant();
        Cart cart = new Cart();
        cart.setMenuItemId(dto.getMenuItemId());
        cart.setQuantity(dto.getQuantity());
        cart.setPrice(dto.getPrice());
        cart.setCustomer(customer);
        cart.setRestaurant(restaurant);
        return cart;
    }
}