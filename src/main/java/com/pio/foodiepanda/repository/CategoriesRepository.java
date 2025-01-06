package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
}
