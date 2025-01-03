package com.pio.foodiepanda.model;

import jakarta.persistence.*;
import jdk.jfr.Category;

import java.math.BigDecimal;

public class MenuItem extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuItemId;

    @Column(name = "item_name")
    private String name;

    @Column(name = "item_description")
    private String description;

    @Column(name = "item_price")
    private BigDecimal price;

    private boolean isAvailable;
    private boolean isVeg;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isVeg() {
        return isVeg;
    }

    public void setVeg(boolean veg) {
        isVeg = veg;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
