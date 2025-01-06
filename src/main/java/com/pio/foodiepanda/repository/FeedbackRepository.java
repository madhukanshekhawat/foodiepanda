package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByRestaurantId(Long restaurantId);
}
