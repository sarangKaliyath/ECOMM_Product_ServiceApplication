package com.ecomm.ecomm_product_serviceapplication.service;

import com.ecomm.ecomm_product_serviceapplication.dto.ProductRequestDto;
import com.ecomm.ecomm_product_serviceapplication.dto.ProductResponseDto;
import com.ecomm.ecomm_product_serviceapplication.model.DeleteType;
import com.ecomm.ecomm_product_serviceapplication.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {

    List<ProductResponseDto> getAllProducts();

    Page<ProductResponseDto> getProducts(Pageable pageable);

    ProductResponseDto getProductById(Long id);

    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    ProductResponseDto replaceProduct(ProductRequestDto productRequestDto, long id);

    DeleteType deleteProduct(Long id);
}
