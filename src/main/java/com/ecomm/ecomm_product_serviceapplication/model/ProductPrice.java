package com.ecomm.ecomm_product_serviceapplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class ProductPrice extends BaseModel{
    private String regionCode;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode = CurrencyCode.USD;

    private Boolean isActive;
}
