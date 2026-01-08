package com.ecomm.ecomm_product_serviceapplication.repository;

import com.ecomm.ecomm_product_serviceapplication.model.Product;
import com.ecomm.ecomm_product_serviceapplication.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByState(State state);

}
