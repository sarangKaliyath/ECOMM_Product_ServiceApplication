package com.ecomm.ecomm_product_serviceapplication.service;

import com.ecomm.ecomm_product_serviceapplication.dto.ProductRequestDto;
import com.ecomm.ecomm_product_serviceapplication.dto.ProductResponseDto;
import com.ecomm.ecomm_product_serviceapplication.model.Product;

import java.util.List;

public interface IProductService {

    List<Product> getAllProducts();

    ProductResponseDto getProductById(Long id);

    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    Product replaceProduct(Product product, long id);

    boolean deleteProduct(Long id);
}
