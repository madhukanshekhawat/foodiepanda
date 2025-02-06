package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.RestaurantAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantAddressRepository extends JpaRepository<RestaurantAddress, Long> {
}
