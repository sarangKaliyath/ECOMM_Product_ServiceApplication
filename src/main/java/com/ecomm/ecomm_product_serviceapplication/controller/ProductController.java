package com.ecomm.ecomm_product_serviceapplication.controller;

import com.ecomm.ecomm_product_serviceapplication.dto.ProductRequestDto;
import com.ecomm.ecomm_product_serviceapplication.dto.ProductResponseDto;
import com.ecomm.ecomm_product_serviceapplication.exceptions.ProductNotFoundException;
import com.ecomm.ecomm_product_serviceapplication.model.Category;
import com.ecomm.ecomm_product_serviceapplication.model.DeleteType;
import com.ecomm.ecomm_product_serviceapplication.model.Product;
import com.ecomm.ecomm_product_serviceapplication.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }
    
    @GetMapping("/list")
    public Page<ProductResponseDto> getProducts(
            @RequestParam(value = "category_id", required = false) Long categoryId,
            @RequestParam(value = "max_price", required = false) Double maxPrice,
            @RequestParam(value = "min_price", required = false) Double minPrice,
            @RequestParam(value = "rating", required = false) Double rating,
            @RequestParam(value = "in_stock", required = false) Boolean inStock,
            @RequestParam(value = "on_sale", required = false) Boolean onSale,
            @PageableDefault(size = 2) Pageable pageable) {
        return productService.getProducts(categoryId, maxPrice, minPrice, rating, inStock, onSale, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable long id) {
        ProductResponseDto product = productService.getProductById(id);
        if (product == null) {
            throw new ProductNotFoundException("Invalid Product ID");
        }

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto productResponseDto = productService.createProduct(productRequestDto);
        return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        DeleteType type = productService.deleteProduct(id);
        return type == DeleteType.SOFT_DELETE ? ResponseEntity.noContent().build() : ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> replaceProduct(@PathVariable long id, @RequestBody ProductRequestDto productRequestDto) {
        return new ResponseEntity<>(productService.replaceProduct(productRequestDto, id), HttpStatus.OK);
    }
}