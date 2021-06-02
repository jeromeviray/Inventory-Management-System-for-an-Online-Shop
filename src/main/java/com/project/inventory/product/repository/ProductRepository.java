package com.project.inventory.product.repository;

import com.project.inventory.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findById(int id);

//    @Query( value = "Select * from product where isDeleted = 0")
//    List<Product> findAll();
}
