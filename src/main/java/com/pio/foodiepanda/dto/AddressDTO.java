package com.pio.foodiepanda.dto;

import com.pio.foodiepanda.utility.AddressLabel;

public class AddressDTO extends BaseDTO {

    private Long addressId;
    private Long userId;
    private String addressLine;
    private String city;
    private String state;
    private AddressLabel label;
    private String postalCode;

    public AddressDTO() {
    }

    public AddressDTO(Long addressId, Long userId, String addressLine, String city, String state, AddressLabel label, String postalCode) {
        this.addressId = addressId;
        this.userId = userId;
        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.label = label;
        this.postalCode = postalCode;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public AddressLabel getLabel() {
        return label;
    }

    public void setLabel(AddressLabel label) {
        this.label = label;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
