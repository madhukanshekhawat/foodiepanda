package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.LoyaltyPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface LoyaltyPointRepository extends JpaRepository<LoyaltyPoints, Long> {
}
