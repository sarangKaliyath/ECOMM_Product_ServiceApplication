package com.ecomm.ecomm_product_serviceapplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ProductAttribute extends BaseModel{
    private String attributeName;

    @Column(length = 200)
    private String attributeValue;
}
