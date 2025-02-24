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
import java.util.logging.Logger;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    Logger logger = Logger.getLogger(CouponServiceImpl.class.getName());

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * Saves a new category created by a user.
     *
     * @param category the category data transfer object containing category details.
     * @param username the username of the user creating the category.
     */
    @Override
    public void saveCategory(CategoriesDTO category, String username) {
        logger.info("Saving category for user: " + username);
        Categories categories = new Categories();
        categories.setName(category.getName());
        categories.setDescription(category.getDescription());
        categories.setCreatedBy(username);
        categoriesRepository.save(categories);
        logger.info("Category saved successfully for user:" + username);
    }

    /**
     * Retrieves all categories created by a specific user.
     *
     * @param username the username of the user whose categories are to be retrieved.
     * @return a list of category data transfer objects.
     */
    @Override
    public List<CategoriesDTO> getAllCategoriesByUser(String username) {
        logger.info("Fetching all categories for user: " + username);
        List<Categories> categories = categoriesRepository.findByCreatedBy(username);
        return categories.stream().map(category -> {
            CategoriesDTO categoriesDTO = new CategoriesDTO();
            categoriesDTO.setCategoryId(category.getCategoryId());
            categoriesDTO.setName(category.getName());
            categoriesDTO.setDescription(category.getDescription());
            return categoriesDTO;
        }).toList();
    }

    /**
     * Retrieves categories for a restaurant based on the principal's email.
     *
     * @param principal the principal object containing the user's email.
     * @return a list of category data transfer objects.
     */
    @Override
    public List<CategoriesDTO> getCategoriesForRestaurant(Principal principal) {
        String email = principal.getName();
        logger.info("Fetching categories for restaurant with email:" + email);
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

    /**
     * Deletes a category created by a specific user.
     *
     * @param id       the ID of the category to be deleted.
     * @param username the username of the user who created the category.
     * @return true if the category was successfully deleted, false otherwise.
     */
    @Override
    public boolean deleteCategoriesByUser(Long id, String username) {
        logger.info("Attempting to delete category with ID:" + id + "for user:" + username);
        Optional<Categories> categoriesOptional = categoriesRepository.findById(id);
        if (categoriesOptional.isPresent()) {
            Categories categories = categoriesOptional.get();
            if (categories.getCreatedBy().equals(username)) {
                categoriesRepository.deleteById(id);
                logger.info("Category with ID:" + id + "deleted successfully for user:" + username);
                return true;
            }
        }
        logger.info("Category with ID:" + id + "not found or user: " + username + "not authorized to delete");
        return false;
    }
}