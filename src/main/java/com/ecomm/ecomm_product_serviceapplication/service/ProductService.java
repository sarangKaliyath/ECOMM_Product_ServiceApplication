package com.ecomm.ecomm_product_serviceapplication.service;

import com.ecomm.ecomm_product_serviceapplication.dto.ProductRequestDto;
import com.ecomm.ecomm_product_serviceapplication.dto.ProductResponseDto;
import com.ecomm.ecomm_product_serviceapplication.exceptions.CategoryNotFoundException;
import com.ecomm.ecomm_product_serviceapplication.exceptions.ProductNotFoundException;
import com.ecomm.ecomm_product_serviceapplication.mapper.ProductMapper;
import com.ecomm.ecomm_product_serviceapplication.model.Category;
import com.ecomm.ecomm_product_serviceapplication.model.DeleteType;
import com.ecomm.ecomm_product_serviceapplication.model.Product;
import com.ecomm.ecomm_product_serviceapplication.model.State;
import com.ecomm.ecomm_product_serviceapplication.repository.CategoryRepo;
import com.ecomm.ecomm_product_serviceapplication.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    public List<ProductResponseDto> getAllProducts() {
        return productRepo.findByState(State.ACTIVE).stream().map(ProductMapper::toResponse).toList();
    }

    @Override
    public Page<ProductResponseDto> getProducts(
            Long categoryId,
            Double maxPrice, Double minPrice,
            Double rating,
            Boolean inStock, Boolean onSale,
            Pageable pageable
    ) {

        // Fetch and filter products based on the provided criteria
        Page<Product> result = productRepo.findProductsByFilters(
                categoryId, maxPrice, minPrice, rating, inStock, onSale, pageable);

        // Convert products into DTOs
        return result.map(ProductMapper::toResponse);
    }

    public ProductResponseDto getProductById(Long id) {
        Optional<Product> productOptional = productRepo.findById(id);
        return productOptional.map(ProductMapper::toResponse).orElse(null);
    }

    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Category category = categoryRepo.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Invalid Category ID."));

        Product product = ProductMapper.toEntity(requestDto, category);

        product.setCategory(category);
        product.setState(State.ACTIVE);

        Product savedProduct = productRepo.save(product);

        return ProductMapper.toResponse(savedProduct);
    }

    public ProductResponseDto replaceProduct(ProductRequestDto productRequestDto, long id) {
        return null;
    }

    public DeleteType deleteProduct(Long id) {
        return null;
    }
}