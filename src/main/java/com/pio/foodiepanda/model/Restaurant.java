package com.pio.foodiepanda.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    @Column(name = "restaurant_name")
    private String name;

    private LocalTime startTime;
    private LocalTime endTime;

    private boolean isAvailable;
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    @JsonIgnore
    private RestaurantAddress restaurantAddress;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "ownerId")
    @JsonIgnore
    private RestaurantOwner restaurantOwner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    @JsonIgnore
    private List<Orders> ordersList;

    @OneToMany(mappedBy = "restaurant")
    private List<MenuItem> menuItems;

    @Transient
    private String firstMenuItemImage;

    @PrePersist
    public void setDefaultTiming() {
        if (this.startTime == null) {
            this.startTime = LocalTime.of(9, 0);
        }
        if (this.endTime == null) {
            this.endTime = LocalTime.of(21, 0);
        }
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public RestaurantOwner getRestaurantOwner() {
        return restaurantOwner;
    }

    public void setRestaurantOwner(RestaurantOwner restaurantOwner) {
        this.restaurantOwner = restaurantOwner;
    }

    public RestaurantAddress getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(RestaurantAddress restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public String getFirstMenuItemImage() {
        return firstMenuItemImage;
    }

    public void setFirstMenuItemImage(String firstMenuItemImage) {
        this.firstMenuItemImage = firstMenuItemImage;
    }
}
