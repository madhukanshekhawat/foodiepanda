package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.OrdersDTO;
import com.pio.foodiepanda.enums.OrderStatus;

import java.security.Principal;
import java.util.List;

public interface OrderService {

    List<OrdersDTO> getOrdersForRestaurant(String email);

    void updateOrderStatus(Long orderId, OrderStatus orderStatus);
}
