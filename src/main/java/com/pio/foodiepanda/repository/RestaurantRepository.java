package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.model.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByRestaurantOwner(RestaurantOwner owner);
}
