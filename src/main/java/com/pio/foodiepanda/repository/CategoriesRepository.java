package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    List<Categories> findByCreatedBy(String createdBy);

}
