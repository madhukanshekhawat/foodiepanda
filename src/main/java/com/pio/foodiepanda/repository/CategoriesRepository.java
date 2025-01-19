package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    Optional<Categories> findByName(String name);
    List<Categories> findAll();
}
