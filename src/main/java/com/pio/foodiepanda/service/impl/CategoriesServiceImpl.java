package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.CategoriesDTO;
import com.pio.foodiepanda.exception.ResourceNotFoundException;
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
        logger.info(MessageConstant.SAVING_CATEGORY_FOR_USER + username);
        Categories categories = new Categories();
        categories.setName(category.getName());
        categories.setDescription(category.getDescription());
        categories.setCreatedBy(username);
        categoriesRepository.save(categories);
        logger.info(MessageConstant.SUCCESSFUL_MESSAGE + username);
    }

    /**
     * Retrieves all categories created by a specific user.
     *
     * @param username the username of the user whose categories are to be retrieved.
     * @return a list of category data transfer objects.
     */
    @Override
    public List<CategoriesDTO> getAllCategoriesByUser(String username) {
        logger.info(MessageConstant.FETCHING_CATEGORY_FOR_USER + username);
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
        logger.info(MessageConstant.FETCHING_CATEGORY_FOR_RESTAURANT + email);
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
     * Updates a category based on the given ID and data.
     *
     * @param id            the ID of the category to update.
     * @param categoriesDTO the DTO containing the updated category information.
     * @return a success message if the update is successful.
     * @throws ResourceNotFoundException if the category is not found.
     */
    @Override
    public String updateCategory(Long id, CategoriesDTO categoriesDTO) {
        logger.info(MessageConstant.UPDATING_CATEGORY + id);
        Optional<Categories> optionalCategories = categoriesRepository.findById(id);
        if (optionalCategories.isPresent()) {
            Categories categories = optionalCategories.get();

            categories.setName(categoriesDTO.getName());
            categories.setDescription(categoriesDTO.getDescription());

            categoriesRepository.save(categories);
            logger.info(MessageConstant.SUCCESSFUL_MESSAGE + id);
            return MessageConstant.SUCCESSFUL_MESSAGE;
        } else {
            logger.severe(MessageConstant.CATEGORY_NOT_FOUND + id);
            throw new ResourceNotFoundException(MessageConstant.NO_MENU_ITEM_FOUND);
        }
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category to retrieve.
     * @return the CategoriesDTO object containing the category information.
     * @throws ResourceNotFoundException if the category is not found.
     */
    @Override
    public CategoriesDTO getCategoryById(Long id) {
        logger.info(MessageConstant.FETCHING_CATEGORY + id);
        Categories category = categoriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.CATEGORY_NOT_FOUND));

        // Create the DTO directly within the method
        CategoriesDTO categoryDTO = new CategoriesDTO();
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());

        logger.info(MessageConstant.SUCCESSFUL_MESSAGE + id);
        return categoryDTO;
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
        logger.info(MessageConstant.ATTEMPTING_TO_DELETE + id + MessageConstant.FOR_USER + username);
        Optional<Categories> categoriesOptional = categoriesRepository.findById(id);
        if (categoriesOptional.isPresent()) {
            Categories categories = categoriesOptional.get();
            if (categories.getCreatedBy().equals(username)) {
                categoriesRepository.deleteById(id);
                logger.info(MessageConstant.CATEGORY_ID + id + MessageConstant.DELETED_SUCCESSFULLY + username);
                return true;
            }
        }
        logger.info(MessageConstant.CATEGORY_ID + id + MessageConstant.NOT_FOUND_OR_USER + username + MessageConstant.UNAUTHORIZED);
        return false;
    }
}