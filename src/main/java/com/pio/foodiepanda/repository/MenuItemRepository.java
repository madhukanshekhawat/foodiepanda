package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByRestaurant_RestaurantId(Long restaurantId);
}
