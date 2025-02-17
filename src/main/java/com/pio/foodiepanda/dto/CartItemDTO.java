package com.pio.foodiepanda.dto;

import com.pio.foodiepanda.model.Restaurant;

public class CartItemDTO {
    private Long cartId;
    private Long menuItemId;
    private String itemName;
    private int quantity;
    private double price;
    private Long customerId;
    private Long restaurantId;

    public CartItemDTO(Long cartId, Long menuItemId, int quantity, double price) {
        this.cartId = cartId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.price = price;
    }

    public CartItemDTO() {

    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getCustomerId(Long customerID) {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
