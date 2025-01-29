package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.CategoriesDTO;
import com.pio.foodiepanda.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoriesService categoriesService;

    @PostMapping("/add")
    public ResponseEntity<String> addCategory(@RequestBody CategoriesDTO category, Principal principal) {
        // Extract the username from the Principal
        String username = principal.getName();
        categoriesService.saveCategory(category, username);
        return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
    }

    @GetMapping("/all")
    public List<CategoriesDTO> getAll(Principal principal) {
        String username = principal.getName();
        return categoriesService.getAllCategoriesByUser(username);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        boolean isDeleted = categoriesService.deleteCategoriesByUser(id, username);

        if (isDeleted) {
            return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to delete this category");
        }
    }

    @GetMapping("/allCategory")
    public ResponseEntity<List<CategoriesDTO>> getCategoriesForRestaurant(Principal principal){
        List<CategoriesDTO> categoriesDTOS = categoriesService.getCategoriesForRestaurant(principal);
        return ResponseEntity.ok(categoriesDTOS);
    }

}