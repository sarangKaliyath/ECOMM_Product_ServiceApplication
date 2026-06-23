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

//    public  List<ProductResponseDto> getAllProducts(Category category) {
//        return null;
//    }

    public ProductResponseDto getProductById(Long id) {
        Optional<Product> productOptional = productRepo.findById(id);
        return productOptional.map(ProductMapper::toResponse).orElse(null);
    }

    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = ProductMapper.toEntity(requestDto);

        Category category = categoryRepo.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Invalid Category ID."));

        product.setCategory(category);
        product.setState(State.ACTIVE);

        Product savedProduct = productRepo.save(product);

        return ProductMapper.toResponse(savedProduct);
    }

    public ProductResponseDto replaceProduct(ProductRequestDto productRequestDto, long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("Invalid Product ID."));

        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setImageUrl(productRequestDto.getImageUrl());
        product.setState(State.ACTIVE);

        if (productRequestDto.getCategoryId() != null && !productRequestDto.getCategoryId().equals(product.getCategory().getId())) {
            Category category = categoryRepo.findById(productRequestDto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Invalid Category ID."));

            product.setCategory(category);
        }

        return ProductMapper.toResponse(productRepo.save(product));
    }

    public DeleteType deleteProduct(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("Invalid Product ID."));

        if (product.getState() == State.ACTIVE) {
            product.setState(State.INACTIVE);
            productRepo.save(product);
            return DeleteType.SOFT_DELETE;
        } else if (product.getState() == State.INACTIVE) {
            productRepo.delete(product);
            return DeleteType.HARD_DELETE;
        }

        throw new IllegalStateException("Invalid Product State.");
    }
}
