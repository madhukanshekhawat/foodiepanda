package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.CategoriesDTO;

import java.security.Principal;
import java.util.List;

public interface CategoriesService {

    void saveCategory(CategoriesDTO category, String username);

    boolean deleteCategoriesByUser(Long id, String username);

    List<CategoriesDTO> getAllCategoriesByUser(String username);

    List<CategoriesDTO> getCategoriesForRestaurant(Principal principal);
}
