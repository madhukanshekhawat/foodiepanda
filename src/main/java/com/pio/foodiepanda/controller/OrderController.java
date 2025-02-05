package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.OrderRequest;
import com.pio.foodiepanda.dto.OrderStatusDTO;
import com.pio.foodiepanda.dto.OrderStatusResponse;
import com.pio.foodiepanda.dto.OrdersDTO;
import com.pio.foodiepanda.enums.OrderStatus;
import com.pio.foodiepanda.model.Orders;
import com.pio.foodiepanda.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<List<OrdersDTO>> getOrder(Principal principal) {
        String email = principal.getName();
        List<OrdersDTO> orders = orderService.getOrdersForRestaurant(email);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customer/all")
    public ResponseEntity<List<OrdersDTO>> getOrderForCustomer(Principal principal){
        String email = principal.getName();
        List<OrdersDTO> ordersDTOS = orderService.getOrdersForCustomer(email);
        return ResponseEntity.ok(ordersDTOS);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId,  @RequestBody OrderStatusDTO orderStatusDTO) {
        orderService.updateOrderStatus(orderId, OrderStatus.valueOf(orderStatusDTO.getStatus()));
        return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
    }

    @GetMapping("/order-status/{orderId}")
    public ResponseEntity<?> getOrderStatus(@PathVariable Long orderId) {
        try {
            OrderStatusResponse orderStatusResponse = orderService.getOrderStatus(orderId);
            return ResponseEntity.ok(orderStatusResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageConstant.ORDER_NOT_FOUND_MESSAGE);
        }
    }

    @PostMapping("/place-order")
    public ResponseEntity<OrdersDTO> placeOrder(@RequestBody OrderRequest orderRequest, Principal principal) {
        String username = principal.getName();
        Long orders = orderService.createOrder(orderRequest, username);
        return ResponseEntity.ok(new OrdersDTO(orders));
    }
}
