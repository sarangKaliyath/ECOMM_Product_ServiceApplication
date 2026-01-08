package com.ecomm.ecomm_product_serviceapplication.mapper;

import com.ecomm.ecomm_product_serviceapplication.dto.CategoryRequestDto;
import com.ecomm.ecomm_product_serviceapplication.dto.CategoryResponseDto;
import com.ecomm.ecomm_product_serviceapplication.model.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryRequestDto dto) {
        if (dto == null) return null;

        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    public static CategoryResponseDto toResponse(Category category) {
        CategoryResponseDto dto = new CategoryResponseDto();

        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());

        return dto;
    }
}
