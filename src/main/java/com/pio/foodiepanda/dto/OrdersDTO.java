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

    public OrdersDTO() {
    }

    public OrdersDTO(Long orderId) {
        this.orderId = orderId;
    }

    public OrdersDTO(Long orderId, double totalAmount, String status, LocalDateTime scheduledTime,String deliveryAddress,String restaurantName) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.scheduledTime = scheduledTime;
        this.deliveryAddress = deliveryAddress;
        this.restaurantName = restaurantName;
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
}
