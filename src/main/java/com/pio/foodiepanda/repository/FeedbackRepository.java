package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
