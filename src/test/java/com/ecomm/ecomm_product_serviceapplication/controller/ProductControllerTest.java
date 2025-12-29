package com.ecomm.ecomm_product_serviceapplication.controller;

import com.ecomm.ecomm_product_serviceapplication.dto.CategoryResponseDto;
import com.ecomm.ecomm_product_serviceapplication.dto.ProductRequestDto;
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
import org.springframework.http.HttpStatus;
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
        List<ProductResponseDto> mockProd = createProducts().stream().map(ProductMapper::toResponse).toList();

        // Stub the MOCK
        when(productService.getAllProducts()).thenReturn(mockProd);

        // Act
        List<ProductResponseDto> products = productController.getAllProducts();
        // Assert
        assertEquals(2, products.size());

        ProductResponseDto firstProduct = products.get(0);
        assertEquals("productOne", firstProduct.getName());
        assertEquals(1L, firstProduct.getId());
        assertEquals(100.0, firstProduct.getPrice());
        assertEquals("http://test-product-one", firstProduct.getImageUrl());
        assertEquals("Test ProductOne Description", firstProduct.getDescription());

        ProductResponseDto secondProduct = products.get(1);
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
        List<ProductResponseDto> products = productController.getAllProducts();

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

    @Test
    public void TestCreateProduct_WithValidProduct_ReturnsProductSuccessfully() {
        // Arrange
        ProductRequestDto req = new ProductRequestDto("Phone",
                "Latest smartphone",
                699.99,
                "http://example.com/phone.jpg",
                2L
        );

        ProductResponseDto responseDto = new ProductResponseDto(
                1L,
                "Phone",
                "Latest smartphone",
                699.99,
                "http://example.com/phone.jpg",
                new CategoryResponseDto(2L, "Electronics", "All electronics items.")
        );

        when(productService.createProduct(req)).thenReturn(responseDto);

        // Act
        ResponseEntity<ProductResponseDto> response = productController.createProduct(req);
        ProductResponseDto product = response.getBody();

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(product);
        assertEquals(1L, product.getId());
        assertEquals("Phone", product.getName());
        assertEquals(699.99, product.getPrice());
        assertEquals("http://example.com/phone.jpg", product.getImageUrl());
        assertEquals(2L, product.getCategory().getId());
        assertEquals("Electronics", product.getCategory().getName());
        assertEquals("All electronics items.", product.getCategory().getDescription());

        verify(productService, times(1)).createProduct(req);
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