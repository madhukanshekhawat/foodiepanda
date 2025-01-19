package com.pio.foodiepanda.dto;

import com.pio.foodiepanda.model.Address;

import java.time.LocalTime;

public class RestaurantDTO extends BaseDTO {
    private Long ownerId;
    private Long restaurantId;
    private String name;

    private String address;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isAvailable;
    private String phoneNumber;

    private AddressDetails addressDetails;

    public static class AddressDetails{
      private Long addressId;
      private String addressLine;
      private String city;
      private String state;
      private String postalCode;

        public Long getAddressId() {
            return addressId;
        }

        public void setAddressId(Long addressId) {
            this.addressId = addressId;
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
    }

    private OwnerDetails ownerDetails;

    public static class OwnerDetails{
        private Long ownerId;
        private String firstName;
        private String lastName;
        private String phoneNumber;

        public Long getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(Long ownerId) {
            this.ownerId = ownerId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }


    public RestaurantDTO() {
    }


    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long restaurantId) {
        this.ownerId = restaurantId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OwnerDetails getOwnerDetails() {
        return ownerDetails;
    }

    public void setOwnerDetails(OwnerDetails ownerDetails) {
        this.ownerDetails = ownerDetails;
    }

    public AddressDetails getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(AddressDetails addressDetails) {
        this.addressDetails = addressDetails;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
