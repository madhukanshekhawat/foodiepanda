package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.dto.CategoryRequest;
import com.pio.foodiepanda.model.Categories;
import com.pio.foodiepanda.service.CategoriesService;
import com.pio.foodiepanda.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CategoriesService categoriesService;


    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody CategoryRequest categoryRequest, HttpServletRequest request) throws IllegalAccessException {
        String token = jwtUtil.extractToken(request); // Extract the JWT token if needed
        String username = jwtUtil.extractUsername(token); // Extract username from token if required

        // Convert the CategoryRequest DTO to an Entity
        Categories category = new Categories();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());

        // Save the category (via service or repository)
        categoriesService.addCategory(category);

        return ResponseEntity.ok("Category added successfully!");
    }
}