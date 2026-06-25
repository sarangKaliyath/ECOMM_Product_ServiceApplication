package com.ecomm.ecomm_product_serviceapplication.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
public class Product extends BaseModel {
    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;
    private String primaryImageUrl;

    private String shortDescription;

    @Column(length = 500)
    private String description;

    private String brand;

    @Column(nullable = false)
    private BigDecimal defaultPrice;

    @Enumerated(EnumType.STRING)
    private CurrencyCode defaultCurrency = CurrencyCode.USD;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus = ProductStatus.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<ProductPrice> prices;

    private Double averageRating = 0.0;
    private Integer reviewCount = 0;

    @Column(nullable = false)
    private String sellerId; // from Auth/User Service

    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus = InventoryStatus.IN_STOCK;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<ProductImage> images;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<ProductAttribute> attributes;

}
