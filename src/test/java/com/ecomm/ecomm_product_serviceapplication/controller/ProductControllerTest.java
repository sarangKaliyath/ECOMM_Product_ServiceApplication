package com.ecomm.ecomm_product_serviceapplication.controller;

import com.ecomm.ecomm_product_serviceapplication.model.Product;
import com.ecomm.ecomm_product_serviceapplication.service.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductControllerTest {

    @MockBean
    private IProductService productService;

    @Test
    public void TestGetAllProducts_ReturnsProductsSuccessfully() {

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
        productTwo.setImageUrl("http://test-product_two");

        // Arrange

        List<Product> products = productService.getAllProducts();

        // Act

        // Assert
    }
}