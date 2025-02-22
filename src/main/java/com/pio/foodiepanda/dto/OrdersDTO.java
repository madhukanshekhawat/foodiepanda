package com.pio.foodiepanda.dto;

import com.pio.foodiepanda.model.Orders;

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
    private String customerFirstName;
    private String customerLastName;
    private String restaurantAddress;
    private String customerContactNumber;
    private String restaurantContactNumber;

    public OrdersDTO() {
    }

    public OrdersDTO(Long orderId) {
        this.orderId = orderId;
    }

    public OrdersDTO(Long orderId, double totalAmount, String status, LocalDateTime scheduledTime, LocalDateTime createdAt, String deliveryAddress, String restaurantName, List<OrderDetailDTO> orderDetails, String customerFirstName, String customerLastName, String restaurantAddress, String restaurantContactNumber, String customerContactNumber) {
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
        this.restaurantAddress = restaurantAddress;
        this.restaurantContactNumber = restaurantContactNumber;
        this.customerContactNumber = customerContactNumber;
    }

    public OrdersDTO(Long orderId, Long customerId, Long addressId, String status, String orderStatus, LocalDateTime scheduledTime, double totalAmount, String deliveryAddress, String userName, String restaurantName, List<OrderDetailDTO> orderDetails, LocalDateTime createdAt, String customerFirstName, String customerLastName, String restaurantAddress, String customerContactNumber, String restaurantContactNumber) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.addressId = addressId;
        this.status = status;
        this.orderStatus = orderStatus;
        this.scheduledTime = scheduledTime;
        this.totalAmount = totalAmount;
        this.deliveryAddress = deliveryAddress;
        this.userName = userName;
        this.restaurantName = restaurantName;
        this.orderDetails = orderDetails;
        this.createdAt = createdAt;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.restaurantAddress = restaurantAddress;
        this.customerContactNumber = customerContactNumber;
        this.restaurantContactNumber = restaurantContactNumber;
    }

    public OrdersDTO(Orders orders, List<OrderDetailDTO> orderDetail) {
        super();
    }

    public OrdersDTO(Long orderId, double totalAmount, String string, LocalDateTime scheduledTime, LocalDateTime createdAt, String city, String name, List<OrderDetailDTO> orderDetailDTOs, String firstName, String lastName, Object o, String city1) {
        super();
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

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getCustomerContactNumber() {
        return customerContactNumber;
    }

    public void setCustomerContactNumber(String customerContactNumber) {
        this.customerContactNumber = customerContactNumber;
    }

    public String getRestaurantContactNumber() {
        return restaurantContactNumber;
    }

    public void setRestaurantContactNumber(String restaurantContactNumber) {
        this.restaurantContactNumber = restaurantContactNumber;
    }
}
