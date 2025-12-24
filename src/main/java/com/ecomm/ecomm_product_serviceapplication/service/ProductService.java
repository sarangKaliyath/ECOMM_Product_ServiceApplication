package com.ecomm.ecomm_product_serviceapplication.service;

import com.ecomm.ecomm_product_serviceapplication.dto.ProductRequestDto;
import com.ecomm.ecomm_product_serviceapplication.dto.ProductResponseDto;
import com.ecomm.ecomm_product_serviceapplication.exceptions.CategoryNotFoundException;
import com.ecomm.ecomm_product_serviceapplication.mapper.ProductMapper;
import com.ecomm.ecomm_product_serviceapplication.model.Category;
import com.ecomm.ecomm_product_serviceapplication.model.Product;
import com.ecomm.ecomm_product_serviceapplication.model.State;
import com.ecomm.ecomm_product_serviceapplication.repository.CategoryRepo;
import com.ecomm.ecomm_product_serviceapplication.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> productOptional = productRepo.findById(id);
        return productOptional.orElse(null);
    }

    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = ProductMapper.toEntity(requestDto);

        Category category = categoryRepo.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Invalid Category ID."));

        product.setCategory(category);

        Product savedProduct = productRepo.save(product);

        return ProductMapper.toResponse(savedProduct);
    }

    public Product replaceProduct(Product product, long id) {
        return null;
    }

    public boolean deleteProduct(Long id) {
        return false;
    }
}
