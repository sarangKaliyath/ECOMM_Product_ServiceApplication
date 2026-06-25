package com.ecomm.ecomm_product_serviceapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private Long id; // Product ID
    private String name; // Name of the product
    private String slug; // Unique identifier for the product
    private String primaryImageUrl; // Primary image set for the product
    private String shortDescription; // Brief description of the product
    private String description; // Detailed description of the product
    private String brand; // Brand associated with the product
    private BigDecimal defaultPrice; // Default price of the product
    private String currencyCode; // Currency code for the default price
    private String productStatus; // Current status of the product (e.g., ACTIVE, DRAFT)
    private String inventoryStatus; // Stock availability (e.g., IN_STOCK, OUT_OF_STOCK)
    private Double averageRating; // Average rating of the product
    private Integer reviewCount; // Number of reviews for the product
    private String sellerId; // Seller/merchant ID who owns the product
    private List<String> imageUrls; // Additional images of the product
    private List<ProductAttributeDto> attributes; // Additional attributes in key-value pairs
    private CategoryResponseDto category; // Associated category details (ID, name, description)
    private Date createdAt; // Timestamp for product creation
}