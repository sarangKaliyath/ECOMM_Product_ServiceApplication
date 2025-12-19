package com.ecomm.ecomm_product_serviceapplication.service;

import com.ecomm.ecomm_product_serviceapplication.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService{


    public List<Category> getAllCategories(){
        return null;
    }

    public Category getCategoryById(Long id){
        return null;
    }

    public Category createCategory(Category category){
        return null;
    }

    public boolean deleteCategory(Long id){
        return false;
    }

    public Category replaceCategory(Category category){
        return null;
    }
}
