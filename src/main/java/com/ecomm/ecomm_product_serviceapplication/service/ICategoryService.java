package com.ecomm.ecomm_product_serviceapplication.service;

import com.ecomm.ecomm_product_serviceapplication.dto.CategoryRequestDto;
import com.ecomm.ecomm_product_serviceapplication.dto.CategoryResponseDto;
import com.ecomm.ecomm_product_serviceapplication.model.Category;

import java.util.List;

public interface ICategoryService {

    List<CategoryResponseDto> getAllCategories();

    CategoryResponseDto getCategoryById(Long id);

    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

    boolean deleteCategory(Long id);

    Category replaceCategory(Category category);
}
