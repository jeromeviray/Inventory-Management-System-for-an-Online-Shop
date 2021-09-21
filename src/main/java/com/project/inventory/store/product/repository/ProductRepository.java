package com.project.inventory.store.product.repository;

import com.project.inventory.store.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findById(int id);

    @Query( value = "SELECT * FROM products WHERE product_is_deleted = 0", nativeQuery = true)
    List<Product> findAllAvailableProducts();

    @Query(value = "SELECT * FROM products p WHERE p.product_is_deleted = 0 AND p.id =:id", nativeQuery = true)
    Optional<Product> findAvailableProductById(@Param("id") int id);

    List<Product> findAll();
}
