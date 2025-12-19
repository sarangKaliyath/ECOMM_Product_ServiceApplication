package com.ecomm.ecomm_product_serviceapplication.repository;

import com.ecomm.ecomm_product_serviceapplication.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
}
