package com.ecomm.ecomm_product_serviceapplication.controller;

import com.ecomm.ecomm_product_serviceapplication.dto.CategoryRequestDto;
import com.ecomm.ecomm_product_serviceapplication.dto.CategoryResponseDto;
import com.ecomm.ecomm_product_serviceapplication.dto.UserDto;
import com.ecomm.ecomm_product_serviceapplication.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public List<CategoryResponseDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable long categoryId) {
        CategoryResponseDto responseDto = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryRequestDto requestDto) {
        CategoryResponseDto responseDto = categoryService.createCategory(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}/{userId}")
    public ResponseEntity<CategoryResponseDto> getCategoryByIdAndUserId(@PathVariable long categoryId, @PathVariable long userId) {

        UserDto userDto = restTemplate.getForObject("http://ECOMM-Auth-Service-Application/user/{userId}", UserDto.class, userId);

        if(userDto == null) return null;
        System.out.println(userDto.getEmail());
        return null;
    }
}
