package com.ecomm.ecomm_product_serviceapplication.controller;

import com.ecomm.ecomm_product_serviceapplication.model.Product;
import com.ecomm.ecomm_product_serviceapplication.service.IProductService;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Equality;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @MockBean
    private IProductService productService;

    @Test
    public void TestGetAllProducts_ReturnsProductsSuccessfully() {

        // Arrange
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

        List<Product> mockProd = List.of(productOne, productTwo);

        // Stub the MOCK
        when(productService.getAllProducts()).thenReturn(mockProd);

        // Act
        List<Product> products = productService.getAllProducts();

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
    }
}