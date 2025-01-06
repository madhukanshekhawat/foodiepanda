package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
