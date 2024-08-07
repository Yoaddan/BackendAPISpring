package com.example.api.repository;

import com.example.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findBySku(String sku);
    Product findByName(String name);
    void deleteById(Long id);
}
