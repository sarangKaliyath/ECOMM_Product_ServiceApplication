package com.ecomm.ecomm_product_serviceapplication.service;

import com.ecomm.ecomm_product_serviceapplication.dto.CategoryRequestDto;
import com.ecomm.ecomm_product_serviceapplication.dto.CategoryResponseDto;
import com.ecomm.ecomm_product_serviceapplication.exceptions.CategoryAlreadyExistsException;
import com.ecomm.ecomm_product_serviceapplication.mapper.CategoryMapper;
import com.ecomm.ecomm_product_serviceapplication.model.Category;
import com.ecomm.ecomm_product_serviceapplication.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        return categories.stream().map(CategoryMapper::toResponse).collect(Collectors.toList());
    }

    public Category getCategoryById(Long id) {
        return null;
    }

    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        Category category = CategoryMapper.toEntity(categoryRequestDto);

        if (categoryRepo.existsByName(category.getName())) {
            throw new CategoryAlreadyExistsException("Category with the name " + category.getName() + " already exists.");
        }
            categoryRepo.save(category);
        return CategoryMapper.toResponse(category);
    }

    public boolean deleteCategory(Long id) {
        return false;
    }

    public Category replaceCategory(Category category) {
        return null;
    }
}
