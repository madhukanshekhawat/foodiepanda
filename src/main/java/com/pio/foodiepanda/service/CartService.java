package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.CartItemDTO;
import com.pio.foodiepanda.model.Cart;

import java.security.Principal;
import java.util.List;

public interface CartService {

    List<CartItemDTO> getCartItemForUser(String email);

    void saveCartItems(List<CartItemDTO> cartItemDTO, Principal principal);
}
