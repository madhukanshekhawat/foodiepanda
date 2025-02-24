package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByRestaurant_RestaurantId(Long restaurantId);

    List<Orders> findByCustomer_CustomerID(Long customerID);
}
