package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
