package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
