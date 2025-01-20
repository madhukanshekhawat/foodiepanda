package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
