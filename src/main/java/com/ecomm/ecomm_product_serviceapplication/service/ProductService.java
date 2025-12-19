package com.ecomm.ecomm_product_serviceapplication.service;

import com.ecomm.ecomm_product_serviceapplication.model.Product;
import com.ecomm.ecomm_product_serviceapplication.model.State;
import com.ecomm.ecomm_product_serviceapplication.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> productOptional = productRepo.findById(id);
        return productOptional.orElse(null);
    }

    public Product createProduct(Product product) {
        Optional<Product> productOptional = productRepo.findById(product.getId());
        if (productOptional.isPresent()) return null;
        return productRepo.save(product);
    }

    public Product replaceProduct(Product product, long id) {
        Optional<Product> productOptional = productRepo.findById(id);
        if (productOptional.isEmpty()) return null;

        product.setId(id);
        product.setCreatedAt(productOptional.get().getCreatedAt());
        return productRepo.save(product);
    }

    public boolean deleteProduct(Long id) {
        Optional<Product> productOptional = productRepo.findById(id);
        if (productOptional.isEmpty()) return false;

        Product product = productOptional.get();

        if (product.getState().equals(State.ACTIVE)) {
            product.setState(State.INACTIVE);
            productRepo.save(product);
        } else {
            productRepo.deleteById(id);
        }

        return true;
    }
}
