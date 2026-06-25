package com.ecomm.ecomm_product_serviceapplication.mapper;

import com.ecomm.ecomm_product_serviceapplication.dto.CategoryResponseDto;
import com.ecomm.ecomm_product_serviceapplication.dto.ProductAttributeDto;
import com.ecomm.ecomm_product_serviceapplication.dto.ProductRequestDto;
import com.ecomm.ecomm_product_serviceapplication.dto.ProductResponseDto;
import com.ecomm.ecomm_product_serviceapplication.model.*;
import java.util.stream.Collectors;

public class ProductMapper {

    // Convert ProductRequestDto to Product entity
    public static Product toEntity(ProductRequestDto dto, Category category) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setSlug(dto.getSlug());
        product.setPrimaryImageUrl(dto.getPrimaryImageUrl());
        product.setShortDescription(dto.getShortDescription());
        product.setDescription(dto.getDescription());
        product.setBrand(dto.getBrand());
        product.setDefaultPrice(dto.getDefaultPrice());
        product.setDefaultCurrency(CurrencyCode.valueOf(dto.getDefaultCurrency()));
        product.setProductStatus(ProductStatus.valueOf(dto.getProductStatus()));
        product.setInventoryStatus(InventoryStatus.valueOf(dto.getInventoryStatus()));
        product.setAverageRating(dto.getAverageRating() != null ? dto.getAverageRating() : 0.0);
        product.setReviewCount(dto.getReviewCount() != null ? dto.getReviewCount() : 0);
        product.setSellerId(dto.getSellerId());
        product.setCategory(category);

        if (dto.getImageUrls() != null) {
            product.setImages(dto.getImageUrls().stream()
                    .map(url -> {
                        ProductImage image = new ProductImage();
                        image.setImageUrl(url);
                        return image;
                    })
                    .collect(Collectors.toList()));
        }

        if (dto.getAttributes() != null) {
            product.setAttributes(dto.getAttributes().stream()
                    .map(attrDto -> {
                        ProductAttribute attribute = new ProductAttribute();
                        attribute.setAttributeName(attrDto.getAttributeName());
                        attribute.setAttributeValue(attrDto.getAttributeValue());
                        return attribute;
                    })
                    .collect(Collectors.toList()));
        }

        return product;
    }

    // Convert Product entity to ProductResponseDto
    public static ProductResponseDto toResponse(Product product) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(product.getId());
        responseDto.setName(product.getName());
        responseDto.setSlug(product.getSlug());
        responseDto.setPrimaryImageUrl(product.getPrimaryImageUrl());
        responseDto.setShortDescription(product.getShortDescription());
        responseDto.setDescription(product.getDescription());
        responseDto.setBrand(product.getBrand());
        responseDto.setDefaultPrice(product.getDefaultPrice());
        responseDto.setCurrencyCode(product.getDefaultCurrency().name());
        responseDto.setProductStatus(product.getProductStatus().name());
        responseDto.setInventoryStatus(product.getInventoryStatus().name());
        responseDto.setAverageRating(product.getAverageRating());
        responseDto.setReviewCount(product.getReviewCount());
        responseDto.setSellerId(product.getSellerId());
        responseDto.setCreatedAt(product.getCreatedAt());
        
        // Map category details
        if (product.getCategory() != null) {
            responseDto.setCategory(new CategoryResponseDto(
                    product.getCategory().getId(),
                    product.getCategory().getName(),
                    product.getCategory().getDescription()
            ));
        }

        // Map product images
        if (product.getImages() != null) {
            responseDto.setImageUrls(product.getImages().stream()
                    .map(ProductImage::getImageUrl)
                    .collect(Collectors.toList()));
        }

        // Map product attributes
        if (product.getAttributes() != null) {
            responseDto.setAttributes(product.getAttributes().stream()
                    .map(attr -> new ProductAttributeDto(attr.getAttributeName(), attr.getAttributeValue()))
                    .collect(Collectors.toList()));
        }
        
        responseDto.setOnSale(product.getOnSale());
        responseDto.setDiscountRate(product.getDiscountRate());

        return responseDto;
    }
}