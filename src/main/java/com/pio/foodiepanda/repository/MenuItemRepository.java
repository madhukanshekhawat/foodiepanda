package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.dto.MenuItemDTO;
import com.pio.foodiepanda.dto.MenuItemResponse;
import com.pio.foodiepanda.model.MenuItem;
import com.pio.foodiepanda.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByRestaurant_RestaurantId(Long restaurantId);

    List<MenuItem> findByRestaurantAndDeletedFalse(Restaurant restaurant);

    MenuItem findFirstByRestaurant_RestaurantId(Long restaurantId);

    @Query("SELECT m FROM MenuItem m JOIN m.restaurant r WHERE m.isAvailable = true AND m.deleted = false AND r.isAvailable = true ORDER BY m.menuItemId ASC")
    List<MenuItem> findIsAvailableMenuItems(Pageable pageable);

}
