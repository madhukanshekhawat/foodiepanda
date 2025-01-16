package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
