package com.pio.foodiepanda.service;

import com.pio.foodiepanda.model.Categories;

import java.util.List;

public interface CategoriesService {

    Categories addCategory(Categories categories);

    List<Categories> getAllCategories();
}
