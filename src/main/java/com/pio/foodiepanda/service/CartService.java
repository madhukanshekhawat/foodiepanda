package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.CartItemDTO;
import com.pio.foodiepanda.model.Cart;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface CartService {

    List<CartItemDTO> getCartItemForUser(String email);
    
    boolean removeItemFromCart(Principal principal, Long menuItemId);

    void clearCartForUser(Principal principal);

    void saveCartItems(Map<Long, Integer> localCart, Principal principal);
}
