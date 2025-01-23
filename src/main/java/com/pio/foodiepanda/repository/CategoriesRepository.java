package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.dto.CategoriesDTO;
import com.pio.foodiepanda.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    List<Categories> findByCreatedBy(String createdBy);

}
