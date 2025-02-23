package com.pio.foodiepanda.dto;

import com.pio.foodiepanda.model.OrderDetail;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDetailDTO extends BaseDTO {

    private Long orderDetailId;
    private Long orderId;
    private String menuItem;
    private String description;
    private double quantity;
    private double price;
    private String image;
    private Long menuItemId;
    private String status;
    private Double totalAmount;
    private Long customerId;
    private Long addressId;
    private Long restaurantId;
    private LocalDateTime createdAt;
    private String customerFullName;
    private String restaurantName;
    private String address;
    private List<OrderDetail> orderDetail;

    public OrderDetailDTO(Long orderId, String status, double totalAmount, Long customerId, String customerFullName, Long addressId, String address, Long restaurantId, String restaurantName, List<OrderDetail> orderDetail, double quantity, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.customerId = customerId;
        this.customerFullName = customerFullName;
        this.addressId = addressId;
        this.address = address;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.orderDetail = orderDetail;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }



    public OrderDetailDTO(Long menuItemId, String name, double quantity, double price) {
        super();
    }

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(OrderDetail orderDetail) {
    }

    public OrderDetailDTO(Long orderId, String status, double totalAmount, Long customerId, Long addressId, Long restaurantId, Long menuItemId, double quantity, double price) {
        this.orderId = orderId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.customerId = customerId;
        this.addressId = addressId;
        this.restaurantId = restaurantId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(String menuItem) {
        this.menuItem = menuItem;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
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

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }
}
