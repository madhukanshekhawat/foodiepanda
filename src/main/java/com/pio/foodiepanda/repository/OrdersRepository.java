package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUserId(Long userId);
    List<Orders> findByRestaurantId(Long restaurantId);
}
