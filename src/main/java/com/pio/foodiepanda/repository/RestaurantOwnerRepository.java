package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, Long> {
    List<RestaurantOwner> findByIsApprovedFalse();
}
