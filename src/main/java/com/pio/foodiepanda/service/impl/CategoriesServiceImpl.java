package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.dto.CategoriesDTO;
import com.pio.foodiepanda.model.Categories;
import com.pio.foodiepanda.repository.CategoriesRepository;
import com.pio.foodiepanda.repository.RestaurantRepository;
import com.pio.foodiepanda.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public void saveCategory(CategoriesDTO category, String username) {
        Categories categories = new Categories();
        categories.setName(category.getName());
        categories.setDescription(category.getDescription());

        categories.setCreatedBy(username);

        categoriesRepository.save(categories);
    }

    @Override
    public List<CategoriesDTO> getAllCategoriesByUser(String username) {
        List<Categories> categories = categoriesRepository.findByCreatedBy(username);
        return categories.stream().map(category -> {
            CategoriesDTO categoriesDTO = new CategoriesDTO();
            categoriesDTO.setCategoryId(category.getCategoryId());
            categoriesDTO.setName(category.getName());
            categoriesDTO.setDescription(category.getDescription());
            return categoriesDTO;
        }).toList();
    }

    @Override
    public List<CategoriesDTO> getCategoriesForRestaurant(Principal principal) {
        String email = principal.getName();

        List<Categories> categories = categoriesRepository.findByCreatedBy(email);
        return categories.stream()
                .map(category -> {
                    CategoriesDTO categoriesDTO = new CategoriesDTO();
                    categoriesDTO.setCategoryId(category.getCategoryId());
                    categoriesDTO.setName(category.getName());
                    categoriesDTO.setDescription(category.getDescription());
                    return categoriesDTO;
                }).toList();
    }


    @Override
    public boolean deleteCategoriesByUser(Long id, String username) {
        Optional<Categories> categoriesOptional = categoriesRepository.findById(id);
        if (categoriesOptional.isPresent()) {
            Categories categories = categoriesOptional.get();
            if (categories.getCreatedBy().equals(username)) {
                categoriesRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }
}
