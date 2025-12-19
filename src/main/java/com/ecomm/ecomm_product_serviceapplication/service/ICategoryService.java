package com.ecomm.ecomm_product_serviceapplication.service;

import com.ecomm.ecomm_product_serviceapplication.model.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category createCategory(Category category);

    boolean deleteCategory(Long id);

    Category replaceCategory(Category category);
}
