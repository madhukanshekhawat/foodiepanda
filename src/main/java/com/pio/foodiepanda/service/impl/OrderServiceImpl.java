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
        logger.info(MessageConstant.FETCHING_ORDER_FOR_RESTAURANT + email);
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
        logger.info(MessageConstant.UPADTING_ORDER_STATUS + orderId);
        Orders orders = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ORDER_NOT_FOUND_MESSAGE));
        orders.setStatus(orderStatus);
        ordersRepository.save(orders);
        logger.info(MessageConstant.SUCCESSFUL_MESSAGE + orderId);
    }

    /**
     * Retrieves the status of an order by its ID.
     *
     * @param orderId the ID of the order to retrieve the status for.
     * @return the OrderStatusResponse object containing the order status.
     * @throws ResourceNotFoundException if the order is not found.
     */
    @Override
    public OrderStatusResponse getOrderStatus(Long orderId) {
        logger.info(MessageConstant.FETCHING_ORDER_STATUS + orderId);
        Orders orders = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ORDER_NOT_FOUND_MESSAGE));
        logger.info(MessageConstant.SUCCESSFUL_MESSAGE + orderId);
        return new OrderStatusResponse(orders.getStatus().toString());
    }

    /**
     * Retrieves a list of orders for a customer by their email.
     *
     * @param email the email of the customer.
     * @return a list of OrdersDTO objects containing the order information.
     */
    @Override
    public List<OrdersDTO> getOrdersForCustomer(String email) {
        logger.info(MessageConstant.FETCHING_ORDER_FOR_CUSTOMER + email);
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

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId the ID of the order to retrieve.
     * @return the OrdersDTO object containing the order information.
     * @throws ResourceNotFoundException if the order is not found.
     */
    @Override
    public OrdersDTO getOrderById(Long orderId) {
        logger.info(MessageConstant.FETCHING_ORDER + orderId);
        Optional<Orders> optionalOrder = ordersRepository.findById(orderId);
        if (!optionalOrder.isPresent()) {
            logger.severe(MessageConstant.ORDER_NOT_FOUND_MESSAGE + orderId);
            throw new ResourceNotFoundException(MessageConstant.ORDER_NOT_FOUND_MESSAGE);
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

        String restaurantAddress = order.getRestaurant().getRestaurantAddress().getAddressLine() + order.getRestaurant().getRestaurantAddress().getCity() + order.getRestaurant().getRestaurantAddress().getState();

        OrdersDTO ordersDTO = new OrdersDTO(
                order.getOrderId(),
                order.getTotalAmount(),
                order.getStatus().toString(),
                order.getScheduledTime(),
                order.getCreatedAt(),
                order.getDeliveryAddress().getAddressLine() + "," + order.getDeliveryAddress().getCity() + "," + order.getDeliveryAddress().getState() + "," + order.getDeliveryAddress().getPostalCode(),
                order.getRestaurant().getName(),
                orderDetailDTOs,
                order.getCustomer().getFirstName(),
                order.getCustomer().getLastName(),
                order.getRestaurant().getRestaurantAddress().getAddressLine() + "," + order.getRestaurant().getRestaurantAddress().getCity() + "," + order.getRestaurant().getRestaurantAddress().getState() + "," + order.getRestaurant().getRestaurantAddress().getPostalCode(),
                order.getRestaurant().getPhoneNumber(),
                order.getCustomer().getPhoneNumber()
        );

        logger.info(MessageConstant.SUCCESSFUL_MESSAGE + orderId);
        return ordersDTO;
    }

    /**
     * Retrieves order details by order ID.
     *
     * @param orderId the ID of the order to retrieve details for.
     * @return a list of OrderDetailDTO objects containing the order details.
     */
    @Override
    public List<OrderDetailDTO> getOrderWithDetail(Long orderId) {
        logger.info(MessageConstant.FETCHING_ORDER_DETAIL + orderId);
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrders_OrderId(orderId);

        // Group order details by orderId
        Map<Long, List<OrderDetail>> groupedOrderDetails = orderDetails.stream()
                .collect(Collectors.groupingBy(od -> od.getOrders().getOrderId()));

        // Create OrderDetailDTO for each orderId
        List<OrderDetailDTO> orderDetailDTOs = groupedOrderDetails.entrySet().stream().map(entry -> {
            List<OrderDetail> details = entry.getValue();

            // Get common order information from the first detail
            OrderDetail firstDetail = details.get(0);

            OrderDetailDTO dto = new OrderDetailDTO(
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
                    firstDetail.getQuantity(),
                    firstDetail.getOrders().getCreatedAt()
            );
            logger.info(MessageConstant.ORDER_DETAIL_CREATED + firstDetail.getOrders().getOrderId());
            return dto;
        }).collect(Collectors.toList());

        logger.info(MessageConstant.SUCCESSFUL_MESSAGE + orderId);
        return orderDetailDTOs;
    }

    /**
     * Cancels an order by its ID.
     *
     * @param orderId the ID of the order to cancel.
     * @throws ResourceNotFoundException if the order is not found.
     */
    @Override
    public void cancelOrder(Long orderId) {
        logger.info(MessageConstant.CANCELLING_ORDER + orderId);
        Orders orders = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ORDER_NOT_FOUND_MESSAGE));
        if (orders.getStatus() != OrderStatus.CANCELLED) {
            orders.setStatus(OrderStatus.CANCELLED);
            ordersRepository.save(orders);
            logger.info(MessageConstant.SUCCESSFUL_MESSAGE + orderId);
        }
    }

    /**
     * Automatically cancels an order if it is not confirmed.
     *
     * @param orderId     the ID of the order to cancel.
     * @param orderStatus the current status of the order.
     * @throws ResourceNotFoundException if the order is not found.
     */
    @Override
    public void autoOrderCancel(Long orderId, OrderStatus orderStatus) {
        logger.info(MessageConstant.AUTO_CANCELLING_ORDER + orderId);
        Orders orders = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ORDER_NOT_FOUND_MESSAGE));
        if (!orders.getStatus().equals(OrderStatus.CONFIRMED)) {
            orders.setStatus(OrderStatus.CANCELLED);
            ordersRepository.save(orders);
            logger.info(MessageConstant.SUCCESSFUL_MESSAGE + orderId);
        }
    }

    /**
     * Creates a new order based on the given request and username.
     *
     * @param orderRequest the request containing the order details.
     * @param username     the username of the customer.
     * @return the ID of the created order.
     * @throws ResourceNotFoundException if the customer, address, or restaurant is not found.
     */
    @Override
    public Long createOrder(OrderRequest orderRequest, String username) {
        logger.info(MessageConstant.CREATING_ORDER + username);
        Customer customer = findCustomerByEmail(username);

        if (customer == null) {
            logger.severe(MessageConstant.CUSTOMER_NOT_FOUND + username);
            throw new ResourceNotFoundException(MessageConstant.USER_NOT_FOUND);
        }

        // Fetch the address entity based on the provided address ID
        Address address = addressRepository.findById(orderRequest.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ADDRESS_NOT_FOUND));

        // Fetch the restaurant entity based on the provided restaurant ID
        Restaurant restaurant = restaurantRepository.findById(orderRequest.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.RESTAURANT_NOT_FOUND));

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setDeliveryAddress(address);
        order.setRestaurant(restaurant);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(calculateTotal(orderRequest.getItems()));
        order.setCreatedAt(LocalDateTime.now());
        order.setScheduledTime(LocalDateTime.now().plusMinutes(30));

        // Save the order
        ordersRepository.save(order);
        logger.info(MessageConstant.SUCCESSFUL_MESSAGE + username);

        // Save order details
        for (OrderItemRequest item : orderRequest.getItems()) {
            // Fetch the MenuItem entity based on the provided menu item ID
            MenuItem menuItem = menuItemRepository.findById(item.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NO_MENU_ITEM_FOUND));

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrders(order);
            orderDetail.setMenuItems(menuItem);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setPrice(item.getPrice());
            orderDetailRepository.save(orderDetail);
            logger.info(MessageConstant.ORDER_SAVED + item.getMenuItemId());
        }
        return order.getOrderId();
    }

    /**
     * Finds a customer by their email.
     *
     * @param username the email of the customer.
     * @return the Customer entity.
     */
    private Customer findCustomerByEmail(String username) {
        return customerRepository.findByEmail(username);
    }

    /**
     * Calculates the total amount for the order.
     *
     * @param itemreq the list of order item requests.
     * @return the total amount.
     */
    private double calculateTotal(List<OrderItemRequest> itemreq) {
        return itemreq.stream().mapToDouble(itemRequests -> itemRequests.getPrice() * itemRequests.getQuantity()).sum();
    }
}

