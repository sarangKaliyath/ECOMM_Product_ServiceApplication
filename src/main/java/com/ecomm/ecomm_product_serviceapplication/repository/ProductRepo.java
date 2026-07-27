package com.ecomm.ecomm_product_serviceapplication.repository;

import com.ecomm.ecomm_product_serviceapplication.model.Product;
import com.ecomm.ecomm_product_serviceapplication.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p " +
           "WHERE (:categoryId IS NULL OR p.category.id = :categoryId) " +
           "AND (:maxPrice IS NULL OR p.defaultPrice <= :maxPrice) " +
           "AND (:minPrice IS NULL OR p.defaultPrice >= :minPrice) " +
           "AND (:rating IS NULL OR p.averageRating >= :rating) " +
           "AND (:inStock IS NULL OR p.inventoryStatus = 'IN_STOCK' AND :inStock = TRUE) " +
           "AND (:onSale IS NULL OR p.onSale = :onSale)")
    Page<Product> findProductsByFilters(@Param("categoryId") Long categoryId,
                                        @Param("maxPrice") Double maxPrice,
                                        @Param("minPrice") Double minPrice,
                                        @Param("rating") Double rating,
                                        @Param("inStock") Boolean inStock,
                                        @Param("onSale") Boolean onSale,
                                        Pageable pageable);
    
    List<Product> findByState(State state);

}