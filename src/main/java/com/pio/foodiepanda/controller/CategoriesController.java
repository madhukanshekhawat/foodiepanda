package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.model.Categories;
import com.pio.foodiepanda.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/owner/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/all")
    public ResponseEntity<List<Categories>> getAllCategories() {
        List<Categories> categories = categoriesService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
