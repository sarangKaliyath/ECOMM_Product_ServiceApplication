package com.ecomm.ecomm_product_serviceapplication.controller;

import com.ecomm.ecomm_product_serviceapplication.dto.CategoryResponseDto;
import com.ecomm.ecomm_product_serviceapplication.dto.ProductRequestDto;
import com.ecomm.ecomm_product_serviceapplication.dto.ProductResponseDto;
import com.ecomm.ecomm_product_serviceapplication.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import(ControllerAdvisor.class)
class ProductControllerMvcTest {

    @MockBean
    private IProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void TestGetAllProducts_ReturnsProductsSuccessfully() throws Exception {
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
    public void TestGetAllProducts_WhenNoProducts_ReturnsEmptyList() throws Exception {
        // Arrange
        when(productService.getAllProducts()).thenReturn(List.of());

        // Act && Assert
        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void TestGetProductById_WithValidProductId_ReturnsProductSuccessfully() throws Exception {
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
    public void TestGetProductById_WithInvalidProductId_ThrowsProductNotFoundException() throws Exception {
        // Arrange
        when(productService.getProductById(3L)).thenReturn(null);

        // Act && Assert
        mockMvc.perform(get("/product/3"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Invalid Product ID"));

        verify(productService, times(1)).getProductById(3L);
    }

    @Test
    public void TestCreateProduct_WithValidProduct_ReturnsProductSuccessfully() throws Exception {
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

        when(productService.createProduct(any(ProductRequestDto.class))).thenReturn(responseDto);

        // Act && Assert
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Phone"))
                .andExpect(jsonPath("$.price").value(699.99))
                .andExpect(jsonPath("$.imageUrl").value("http://example.com/phone.jpg"))
                .andExpect(jsonPath("$.category.id").value(2))
                .andExpect(jsonPath("$.category.name").value("Electronics"));

        verify(productService, times(1)).createProduct(any(ProductRequestDto.class));
    }
}