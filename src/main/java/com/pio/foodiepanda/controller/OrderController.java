package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.OrderStatusDTO;
import com.pio.foodiepanda.dto.OrdersDTO;
import com.pio.foodiepanda.enums.OrderStatus;
import com.pio.foodiepanda.model.Orders;
import com.pio.foodiepanda.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<OrdersDTO>> getOrder(Principal principal){
        String email = principal.getName();
        List<OrdersDTO> orders = orderService.getOrdersForRestaurant(email);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatusDTO orderStatusDTO){
        orderService.updateOrderStatus(orderId,OrderStatus.valueOf(orderStatusDTO.getStatus()));
        return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
    }
}
