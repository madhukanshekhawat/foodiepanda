package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
