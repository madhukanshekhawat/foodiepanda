package com.pio.foodiepanda.model;

import jakarta.persistence.*;

import java.time.LocalTime;

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
    private Address address;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "ownerID")
    private RestaurantOwner restaurantOwner;

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public RestaurantOwner getRestaurantOwner() {
        return restaurantOwner;
    }

    public void setRestaurantOwner(RestaurantOwner restaurantOwner) {
        this.restaurantOwner = restaurantOwner;
    }

}
