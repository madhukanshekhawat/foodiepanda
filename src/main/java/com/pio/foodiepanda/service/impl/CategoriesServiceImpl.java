package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.model.Categories;
import com.pio.foodiepanda.repository.CategoriesRepository;
import com.pio.foodiepanda.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }
}
