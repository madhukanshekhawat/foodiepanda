package com.pio.foodiepanda.dto;

import java.math.BigDecimal;

public class CartItemDTO {
    private Long cartId;
    private Long menuItemId;
    private String name;
    private int quantity;
    private BigDecimal price;
    private Long customerId;
    private Long restaurantId;
    private boolean isAvailable;

    public CartItemDTO(Long cartId, Long menuItemId, int quantity, BigDecimal price, String name, Long customerId, Long restaurantId, boolean isAvailable) {
        this.cartId = cartId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.isAvailable = isAvailable;
    }

    public CartItemDTO(Long cartId, Long menuItemId, int quantity, BigDecimal price, String name) {
        this.cartId = cartId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getItemName() {
        return name;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
