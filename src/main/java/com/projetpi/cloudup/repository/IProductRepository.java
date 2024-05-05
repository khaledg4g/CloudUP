package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByPriceBetween(double minPrice, double maxPrice);
    List<Product> findByNameContaining(String keyword);
}
