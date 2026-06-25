package com.ecomm.ecomm_product_serviceapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    private String name;
    private String slug; // Unique slug for the product URL
    private String primaryImageUrl; // Primary image for the product
    private String shortDescription; // Short description of the product
    private String description; // Detailed description
    private String brand; // Brand name of the product
    private BigDecimal defaultPrice; // Default price of the product
    private String defaultCurrency; // Currency for the default price (e.g., USD)
    private String productStatus; // Product status (e.g., ACTIVE, DRAFT)
    private String inventoryStatus; // Inventory status (e.g., IN_STOCK, OUT_OF_STOCK)
    private Long categoryId; // Category ID for linking the product to a category
    private Double averageRating; // Average rating of the product
    private Integer reviewCount; // Number of reviews for the product
    private String sellerId; // Reference to the seller's ID
    private List<String> imageUrls; // List of additional product image URLs
    private List<ProductAttributeDto> attributes; // Additional attributes as key-value DTOs
}