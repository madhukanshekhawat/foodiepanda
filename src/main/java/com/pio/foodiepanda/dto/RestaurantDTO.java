package com.pio.foodiepanda.dto;

import java.time.LocalTime;

public class RestaurantDTO extends BaseDTO{
    private Long restaurantId;
    private String name;
    private String ownerName;

    private String  address;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isAvailable;
    private String phoneNumber;

    public RestaurantDTO() {
    }

    public RestaurantDTO(Long restaurantId, String name, String ownerName, String address, LocalTime startTime, LocalTime endTime, boolean isAvailable, String phoneNumber) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.ownerName = ownerName;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = isAvailable;
        this.phoneNumber = phoneNumber;
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
