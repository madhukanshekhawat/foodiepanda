package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.*;
import com.pio.foodiepanda.enums.OrderStatus;
import com.pio.foodiepanda.exception.ResourceNotFoundException;
import com.pio.foodiepanda.model.*;
import com.pio.foodiepanda.repository.*;
import com.pio.foodiepanda.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves a list of orders for a specific restaurant based on the owner's email.
     *
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
     *
     * @param orderId     the ID of the order to be updated.
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

    @Override
    public OrderStatusResponse getOrderStatus(Long orderId) {
        Orders orders = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ORDER_NOT_FOUND_MESSAGE));
        return new OrderStatusResponse(orders.getStatus().toString());
    }

    @Override
    public List<OrdersDTO> getOrdersForCustomer(String email) {
        logger.info("Fetching orders for Customer with owner email:" + email);
        User user = userRepository.findByEmail(email);
        List<Orders> orders = ordersRepository.findByCustomer_CustomerID(user.getCustomer().getCustomerID());
        return orders.stream().map(order -> {
            OrdersDTO dto = new OrdersDTO();
            dto.setOrderId(order.getOrderId());
            dto.setOrderStatus(order.getStatus().toString());
            dto.setDeliveryAddress(order.getDeliveryAddress().getAddressLine() + " " + order.getDeliveryAddress().getCity() + " " + order.getDeliveryAddress().getState() + " " + order.getDeliveryAddress().getPostalCode());
            dto.setUserName(order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName());
            dto.setScheduledTime(order.getScheduledTime());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setCreatedAt(order.getCreatedAt());
            dto.setRestaurantName(order.getRestaurant().getName());

            // Fetch order details
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrders_OrderId(order.getOrderId());
            List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream().map(detail -> {
                OrderDetailDTO detailDTO = new OrderDetailDTO();
                detailDTO.setOrderDetailId(detail.getOrderDetailId());
                detailDTO.setQuantity(detail.getQuantity());
                detailDTO.setPrice(detail.getPrice());
                detailDTO.setMenuItem(detail.getMenuItems().getName());
                return detailDTO;
            }).collect(Collectors.toList());
            dto.setOrderDetails(orderDetailDTOs);

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public OrdersDTO getOrderById(Long orderId) {
        Optional<Orders> optionalOrder = ordersRepository.findById(orderId);
        if (!optionalOrder.isPresent()) {
            throw new ResourceNotFoundException("Order not found");
        }

        Orders order = optionalOrder.get();
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrders_OrderId(order.getOrderId());
        List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream().map(detail -> {
            OrderDetailDTO detailDTO = new OrderDetailDTO();
            detailDTO.setOrderDetailId(detail.getOrderDetailId());
            detailDTO.setQuantity(detail.getQuantity());
            detailDTO.setPrice(detail.getPrice());
            detailDTO.setMenuItem(detail.getMenuItems().getName());
            return detailDTO;
        }).collect(Collectors.toList());

        RestaurantAddress restaurantAddress = order.getRestaurant().getRestaurantAddress();

        return new OrdersDTO(
                order.getOrderId(),
                order.getTotalAmount(),
                order.getStatus().toString(),
                order.getScheduledTime(),
                order.getCreatedAt(),
                order.getDeliveryAddress().getCity(),
                order.getRestaurant().getName(),
                orderDetailDTOs,
                order.getCustomer().getFirstName(),
                order.getCustomer().getLastName(),
                order.getRestaurant().getRestaurantAddress().getCity()
        );
    }

    @Override
    public List<OrderDetailDTO> getOrderWithDetail(Long orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrders_OrderId(orderId);

        // Group order details by orderId
        Map<Long, List<OrderDetail>> groupedOrderDetails = orderDetails.stream()
                .collect(Collectors.groupingBy(od -> od.getOrders().getOrderId()));

        // Create OrderDetailDTO for each orderId
        return groupedOrderDetails.entrySet().stream().map(entry -> {
            List<OrderDetail> details = entry.getValue();

            // Get common order information from the first detail
            OrderDetail firstDetail = details.get(0);

            return new OrderDetailDTO(
                    firstDetail.getOrders().getOrderId(),
                    firstDetail.getOrders().getStatus().toString(),
                    firstDetail.getOrders().getTotalAmount(),
                    firstDetail.getOrders().getCustomer().getCustomerID(),
                    firstDetail.getOrders().getCustomer().getFirstName() + " " + firstDetail.getOrders().getCustomer().getLastName(),
                    firstDetail.getOrders().getDeliveryAddress().getAddressId(),
                    firstDetail.getOrders().getDeliveryAddress().getAddressLine() + ", " + firstDetail.getOrders().getDeliveryAddress().getCity(),
                    firstDetail.getOrders().getRestaurant().getRestaurantId(),
                    firstDetail.getOrders().getRestaurant().getName(),
                    details,
                    firstDetail.getQuantity()
            );
        }).collect(Collectors.toList());
    }

    @Override
    public Long createOrder(OrderRequest orderRequest, String username) {
        Customer customer = findCustomerByEmail(username);

        if (customer == null) {
            throw new ResourceNotFoundException(MessageConstant.USER_NOT_FOUND);
        }

        // Fetch the address entity based on the provided address ID
        Address address = addressRepository.findById(orderRequest.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        // Fetch the restaurant entity based on the provided restaurant ID
        Restaurant restaurant = restaurantRepository.findById(orderRequest.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setDeliveryAddress(address); // Set the address entity
        order.setRestaurant(restaurant); // Set the restaurant entity
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(calculateTotal(orderRequest.getItems()));
        order.setCreatedAt(LocalDateTime.now());
        order.setScheduledTime(LocalDateTime.now().plusMinutes(30));

        // Save the order
        ordersRepository.save(order);

        // Save order details
        for (OrderItemRequest item : orderRequest.getItems()) {
            // Fetch the MenuItem entity based on the provided menu item ID
            MenuItem menuItem = menuItemRepository.findById(item.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrders(order); // Set the Orders entity
            orderDetail.setMenuItems(menuItem); // Set the MenuItem entity
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setPrice(item.getPrice());
            orderDetailRepository.save(orderDetail);
        }
        return order.getOrderId();
    }

    private Customer findCustomerByEmail(String username) {
        return customerRepository.findByEmail(username);
    }

    private double calculateTotal(List<OrderItemRequest> itemreq) {
        return itemreq.stream().mapToDouble(itemRequests -> itemRequests.getPrice() * itemRequests.getQuantity()).sum();
    }

}

