package com.pio.foodiepanda.dto;

import java.math.BigDecimal;

public class MenuItemResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private String restaurantName;
    private boolean restaurantAvailable;
    private String itemImage;

    public MenuItemResponse(Long id, String name, BigDecimal price, String restaurantName, boolean restaurantAvailable, String itemImage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.restaurantName = restaurantName;
        this.restaurantAvailable = restaurantAvailable;
        this.itemImage = itemImage;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public boolean isRestaurantAvailable() {
        return restaurantAvailable;
    }

    public void setRestaurantAvailable(boolean restaurantAvailable) {
        this.restaurantAvailable = restaurantAvailable;
    }
}
