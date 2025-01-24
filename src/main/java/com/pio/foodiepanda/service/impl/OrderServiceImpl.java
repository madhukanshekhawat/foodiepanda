package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.dto.OrderDetailDTO;
import com.pio.foodiepanda.dto.OrdersDTO;
import com.pio.foodiepanda.enums.OrderStatus;
import com.pio.foodiepanda.exception.ResourceNotFoundException;
import com.pio.foodiepanda.model.OrderDetail;
import com.pio.foodiepanda.model.Orders;
import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.repository.OrdersRepository;
import com.pio.foodiepanda.repository.RestaurantRepository;
import com.pio.foodiepanda.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<OrdersDTO> getOrdersForRestaurant(String email) {
        Restaurant restaurant = restaurantRepository.findByOwnerEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("No Restaurant Found"));
        List<Orders> orders = ordersRepository.findByRestaurant_RestaurantId(restaurant.getRestaurantId());
        return orders.stream().map(order -> {
            OrdersDTO dto = new OrdersDTO();
            dto.setOrderId(order.getOrderId());
            dto.setOrderStatus(order.getStatus().toString());
            dto.setDeliveryAddress(order.getDeliveryAddress().getAddressLine() + " " + order.getDeliveryAddress().getCity() + " " + order.getDeliveryAddress().getState() + " " + order.getDeliveryAddress().getPostalCode());
            dto.setUserName(order.getRestaurant().getRestaurantOwner().getFirstName() + " " + order.getRestaurant().getRestaurantOwner().getLastName());
            dto.setScheduledTime(order.getScheduledTime());
            dto.setTotalAmount(order.getTotalAmount());

            OrderDetail orderDetail = order.getOrderDetails();
            OrderDetailDTO detailDTO = new OrderDetailDTO();
            detailDTO.setMenuItem(orderDetail.getMenuItems().getName());
            detailDTO.setDescription(orderDetail.getMenuItems().getDescription());
            detailDTO.setImage(orderDetail.getMenuItems().getImage());
            detailDTO.setQuantity(orderDetail.getQuantity());
            detailDTO.setPrice(orderDetail.getPrice());

            dto.setOrderDetails(List.of(detailDTO));
            return dto;
        }).toList();
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Orders orders = ordersRepository.findById(orderId)
                .orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        orders.setStatus(orderStatus);
        ordersRepository.save(orders);
    }
}
