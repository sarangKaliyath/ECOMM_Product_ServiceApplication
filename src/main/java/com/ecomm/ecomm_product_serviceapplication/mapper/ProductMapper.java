package com.ecomm.ecomm_product_serviceapplication.mapper;

import com.ecomm.ecomm_product_serviceapplication.dto.CategoryResponseDto;
import com.ecomm.ecomm_product_serviceapplication.dto.ProductRequestDto;
import com.ecomm.ecomm_product_serviceapplication.dto.ProductResponseDto;
import com.ecomm.ecomm_product_serviceapplication.model.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequestDto dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImageUrl());
        product.setPrice(dto.getPrice());

        return product;
    }

    public static ProductResponseDto toResponse(Product product) {
        ProductResponseDto dto = new ProductResponseDto();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setImageUrl(product.getImageUrl());
        dto.setPrice(product.getPrice());

        if (product.getCategory() != null) {
            CategoryResponseDto catDto = new CategoryResponseDto();
            catDto.setId(product.getCategory().getId());
            catDto.setName(product.getCategory().getName());
            catDto.setDescription(product.getCategory().getDescription());
            dto.setCategory(catDto);
        }

        return dto;
    }

}
