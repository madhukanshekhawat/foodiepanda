package com.pio.foodiepanda.model;

import com.pio.foodiepanda.enums.DeliveryAddressLabel;
import jakarta.persistence.*;

@Entity
public class RestaurantAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantAddressId;

    @Column(name = "address_line", nullable = false, length = 255)
    private String addressLine;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "state", nullable = false, length = 100)
    private String state;

    @Column(name = "postal_code", nullable = false, length = 10)
    private String postalCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_address_label", nullable = false)
    private DeliveryAddressLabel addressLabel;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", nullable = false)
    private RestaurantOwner restaurantOwner;

    @OneToOne
    private Restaurant restaurant;

    public Long getRestaurantAddressId() {
        return restaurantAddressId;
    }

    public void setRestaurantAddressId(Long restaurantAddressId) {
        this.restaurantAddressId = restaurantAddressId;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public DeliveryAddressLabel getAddressLabel() {
        return addressLabel;
    }

    public void setAddressLabel(DeliveryAddressLabel addressLabel) {
        this.addressLabel = addressLabel;
    }

    public RestaurantOwner getRestaurantOwner() {
        return restaurantOwner;
    }

    public void setRestaurantOwner(RestaurantOwner restaurantOwner) {
        this.restaurantOwner = restaurantOwner;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
