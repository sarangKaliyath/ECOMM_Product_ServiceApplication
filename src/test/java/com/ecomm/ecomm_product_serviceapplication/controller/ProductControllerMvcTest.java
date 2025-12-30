package com.ecomm.ecomm_product_serviceapplication.controller;

import com.ecomm.ecomm_product_serviceapplication.dto.CategoryResponseDto;
import com.ecomm.ecomm_product_serviceapplication.dto.ProductResponseDto;
import com.ecomm.ecomm_product_serviceapplication.service.IProductService;
import com.ecomm.ecomm_product_serviceapplication.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerMvcTest {

    @MockBean
    private IProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void TestGetAllProducts_ReturnsProductsSuccessfully() throws Exception {
        List<ProductResponseDto> mockProducts = List.of(
                new ProductResponseDto(1L, "productOne", "Test ProductOne Description", 100.0, "http://test-product-one",
                        new CategoryResponseDto(1L, "Category1", "Description1")),
                new ProductResponseDto(2L, "productTwo", "Test ProductTwo Description", 200.0, "http://test-product-two",
                        new CategoryResponseDto(2L, "Category2", "Description2"))
        );

        when(productService.getAllProducts()).thenReturn(mockProducts);

        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("productOne"))
                .andExpect(jsonPath("$[0].category.id").value(1))
                .andExpect(jsonPath("$[0].category.name").value("Category1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("productTwo"))
                .andExpect(jsonPath("$[1].category.id").value(2))
                .andExpect(jsonPath("$[1].category.name").value("Category2"));


        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void TestGetProductById_WithValidProductId_ReturnsProductSuccessfully() throws Exception {
        // Arrange
        ProductResponseDto mockProduct = new ProductResponseDto(1L, "productOne", "Test ProductOne Description", 100.0, "http://test-product-one",
                new CategoryResponseDto(1L, "Category1", "Description1"));

        when(productService.getProductById(1L)).thenReturn(mockProduct);

        // Act && Assert

        mockMvc.perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("productOne"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.category.id").value(1))
                .andExpect(jsonPath("$.category.name").value("Category1"));

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void createProduct() {
    }
}