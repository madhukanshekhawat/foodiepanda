package com.pio.foodiepanda.dto;

import java.util.List;

public class CartSyncRequest {

    private String email;
    private List<CartItemDTO> cartItemDTOS;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CartItemDTO> getCartItemDTOS() {
        return cartItemDTOS;
    }

    public void setCartItemDTOS(List<CartItemDTO> cartItemDTOS) {
        this.cartItemDTOS = cartItemDTOS;
    }
}

