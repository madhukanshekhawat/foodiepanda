package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.OrderDetailDTO;
import com.pio.foodiepanda.dto.OrderRequest;
import com.pio.foodiepanda.dto.OrderStatusResponse;
import com.pio.foodiepanda.dto.OrdersDTO;
import com.pio.foodiepanda.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    List<OrdersDTO> getOrdersForRestaurant(String email);

    void updateOrderStatus(Long orderId, OrderStatus orderStatus);

    Long createOrder(OrderRequest orderRequest, String username);

    OrderStatusResponse getOrderStatus(Long orderId);

    List<OrdersDTO> getOrdersForCustomer(String email);

    OrdersDTO getOrderById(Long orderId);

    List<OrderDetailDTO> getOrderWithDetail(Long orderId);

    void cancelOrder(Long orderId);

    void autoOrderCancel(Long orderId, OrderStatus orderStatus);
}
