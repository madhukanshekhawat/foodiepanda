package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrders_OrderId(Long orderId);
}
