package com.pio.foodiepanda.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrdersDTO extends BaseDTO {

    private Long orderId;
    private Long customerId;
    private Long addressId;
    private String status;
    private String orderStatus;
    private LocalDateTime scheduledTime;
    private double totalAmount;
    private String deliveryAddress;
    private String userName;
    private String restaurantName;
    private List<OrderDetailDTO> orderDetails;
    private LocalDateTime createdAt;
    private String customerFirstName; // Add this field
    private String customerLastName;  // Add this field


    public OrdersDTO() {
    }

    public OrdersDTO(Long orderId) {
        this.orderId = orderId;
    }

    public OrdersDTO(Long orderId, double totalAmount, String status, LocalDateTime scheduledTime, String deliveryAddress, String restaurantName, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.scheduledTime = scheduledTime;
        this.deliveryAddress = deliveryAddress;
        this.restaurantName = restaurantName;
        this.createdAt = createdAt;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
    }

    public OrdersDTO(Long orderId, double totalAmount, String status, LocalDateTime scheduledTime, LocalDateTime createdAt, String deliveryAddress, String restaurantName, List<OrderDetailDTO> orderDetails, String customerFirstName, String customerLastName) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.scheduledTime = scheduledTime;
        this.createdAt = createdAt;
        this.deliveryAddress = deliveryAddress;
        this.restaurantName = restaurantName;
        this.orderDetails = orderDetails;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }
}
