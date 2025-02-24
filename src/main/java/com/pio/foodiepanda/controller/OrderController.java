package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.*;
import com.pio.foodiepanda.enums.OrderStatus;
import com.pio.foodiepanda.model.OrderDetail;
import com.pio.foodiepanda.model.Orders;
import com.pio.foodiepanda.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private InvoiceGenerator invoiceGenerator;

    @GetMapping("/generate-invoice/{orderId}")
    public ResponseEntity<InputStreamResource> generateInvoice(@PathVariable Long orderId) {
        OrdersDTO order = orderService.getOrderById(orderId);
        try {
            String filePath = invoiceGenerator.generateInvoice(order);
            File file = new File(filePath);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/customer/all")
    public ResponseEntity<List<OrdersDTO>> getOrderForCustomer(Principal principal) {
        String email = principal.getName();
        List<OrdersDTO> ordersDTOS = orderService.getOrdersForCustomer(email);
        return ResponseEntity.ok(ordersDTOS);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatusDTO orderStatusDTO) {
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
        if (orderRequest.getRestaurantId() == null) {
            return (ResponseEntity<OrdersDTO>) ResponseEntity.badRequest();
        }
        String username = principal.getName();
        Long orders = orderService.createOrder(orderRequest, username);
        return ResponseEntity.ok(new OrdersDTO(orders));
    }

    @GetMapping("/get")
    public ResponseEntity<List<OrderDetailDTO>> getOrderByOrderId(@RequestParam("orderId") Long orderId){
        return ResponseEntity.ok((List<OrderDetailDTO>) orderService.getOrderWithDetail(orderId));
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
    }

    @PutMapping("/auto-cancel/{orderId}")
    public ResponseEntity<String> autoCancelOrder(@PathVariable Long orderId, @RequestBody OrderStatusDTO orderStatusDTO){
        orderService.autoOrderCancel(orderId, OrderStatus.valueOf(orderStatusDTO.getStatus()));
        return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
    }
}
