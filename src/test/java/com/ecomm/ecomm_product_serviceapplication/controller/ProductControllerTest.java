package com.ecomm.ecomm_product_serviceapplication.controller;

import com.ecomm.ecomm_product_serviceapplication.dto.ProductResponseDto;
import com.ecomm.ecomm_product_serviceapplication.exceptions.ProductNotFoundException;
import com.ecomm.ecomm_product_serviceapplication.mapper.ProductMapper;
import com.ecomm.ecomm_product_serviceapplication.model.Product;
import com.ecomm.ecomm_product_serviceapplication.service.IProductService;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Equality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
    private IProductService productService;

    @Test
    public void TestGetAllProducts_ReturnsProductsSuccessfully() {

        // Arrange
        List<Product> mockProd = createProducts();

        // Stub the MOCK
        when(productService.getAllProducts()).thenReturn(mockProd);

        // Act
        List<Product> products = productController.getAllProducts();

        // Assert
        assertEquals(2, products.size());

        Product firstProduct = products.get(0);
        assertEquals("productOne", firstProduct.getName());
        assertEquals(1L, firstProduct.getId());
        assertEquals(100.0, firstProduct.getPrice());
        assertEquals("http://test-product-one", firstProduct.getImageUrl());
        assertEquals("Test ProductOne Description", firstProduct.getDescription());

        Product secondProduct = products.get(1);
        assertEquals("productTwo", secondProduct.getName());
        assertEquals(2L, secondProduct.getId());
        assertEquals(200.0, secondProduct.getPrice());
        assertEquals("http://test-product-two", secondProduct.getImageUrl());
        assertEquals("Test ProductTwo Description", secondProduct.getDescription());

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void TestGetAllProducts_WhenNoProducts_ReturnsEmptyList() {
        // Arrange
        when(productService.getAllProducts()).thenReturn(List.of());

        // Act
        List<Product> products = productController.getAllProducts();

        // Assert
        assertEquals(0, products.size());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void TestGetProductById_WithValidProductId_ReturnsProductSuccessfully() {
        // Arrange
        List<ProductResponseDto> mockProd = createProducts().stream().map(ProductMapper::toResponse).toList();
        when(productService.getProductById(1L)).thenReturn(mockProd.get(0));

        // Act
        ResponseEntity<ProductResponseDto> productEntity = productController.getProductById(1L);
        ProductResponseDto product = productEntity.getBody();

        // Assert
        assertEquals(1L, product.getId());
        assertEquals("productOne", product.getName());
        assertEquals(100.0, product.getPrice());
        assertEquals("Test ProductOne Description", product.getDescription());
        assertEquals("http://test-product-one", product.getImageUrl());

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    public void TestGetProductById_WithInvalidProductId_ThrowsProductNotFoundException() {
        // Arrange
        when(productService.getProductById(3L)).thenReturn(null);

        // Act && Assert
        ProductNotFoundException ex = assertThrows(ProductNotFoundException.class, () -> productController.getProductById(3L));

        assertEquals("Invalid Product ID", ex.getMessage());
        verify(productService, times(1)).getProductById(3L);
    }


    public List<Product> createProducts() {
        Product productOne = new Product();
        productOne.setName("productOne");
        productOne.setId(1L);
        productOne.setDescription("Test ProductOne Description");
        productOne.setPrice(100.0);
        productOne.setImageUrl("http://test-product-one");

        Product productTwo = new Product();
        productTwo.setName("productTwo");
        productTwo.setId(2L);
        productTwo.setDescription("Test ProductTwo Description");
        productTwo.setPrice(200.0);
        productTwo.setImageUrl("http://test-product-two");

        return List.of(productOne, productTwo);
    }
}