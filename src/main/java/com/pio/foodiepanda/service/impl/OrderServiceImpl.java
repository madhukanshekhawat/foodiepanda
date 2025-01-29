package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.OrderDetailDTO;
import com.pio.foodiepanda.dto.OrdersDTO;
import com.pio.foodiepanda.enums.OrderStatus;
import com.pio.foodiepanda.exception.ResourceNotFoundException;
import com.pio.foodiepanda.model.OrderDetail;
import com.pio.foodiepanda.model.Orders;
import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.repository.OrderDetailRepository;
import com.pio.foodiepanda.repository.OrdersRepository;
import com.pio.foodiepanda.repository.RestaurantRepository;
import com.pio.foodiepanda.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    Logger logger = Logger.getLogger(OrderServiceImpl.class.getName());

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    /**
     * Retrieves a list of orders for a specific restaurant based on the owner's email.
     * @param email the email of the restaurant owner.
     * @return a list of OrdersDTO objects representing the orders.
     */
    @Override
    public List<OrdersDTO> getOrdersForRestaurant(String email) {
        logger.info("Fetching orders for restaurant with owner email:" + email);
        Restaurant restaurant = restaurantRepository.findByOwnerEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.RESTAURANT_NOT_FOUND_MESSAGE));
        List<Orders> orders = ordersRepository.findByRestaurant_RestaurantId(restaurant.getRestaurantId());
        return orders.stream().map(order -> {
            OrdersDTO dto = new OrdersDTO();
            dto.setOrderId(order.getOrderId());
            dto.setOrderStatus(order.getStatus().toString());
            dto.setDeliveryAddress(order.getDeliveryAddress().getAddressLine() + " " + order.getDeliveryAddress().getCity() + " " + order.getDeliveryAddress().getState() + " " + order.getDeliveryAddress().getPostalCode());
            dto.setUserName(order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName());
            dto.setScheduledTime(order.getScheduledTime());
            dto.setTotalAmount(order.getTotalAmount());

            // Fetch order details
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrders_OrderId(order.getOrderId());
            List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream().map(detail -> {
                OrderDetailDTO detailDTO = new OrderDetailDTO();
                detailDTO.setOrderDetailId(detail.getOrderDetailId());
                detailDTO.setQuantity(detail.getQuantity());
                detailDTO.setPrice(detail.getPrice());
                detailDTO.setMenuItem(detail.getMenuItems().getName());
                detailDTO.setImage(detail.getMenuItems().getImage());
                return detailDTO;
            }).collect(Collectors.toList());
            dto.setOrderDetails(orderDetailDTOs);

            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * Updates the status of an order based on the provided order ID and new status.
     * @param orderId the ID of the order to be updated.
     * @param orderStatus the new status to be set for the order.
     */
    @Override
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        logger.info("Updating order status for order ID:" + orderId);
        Orders orders = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ORDER_NOT_FOUND_MESSAGE));
        orders.setStatus(orderStatus);
        ordersRepository.save(orders);
        logger.info("Order status updated successfully for order ID:" + orderId);
    }
}

