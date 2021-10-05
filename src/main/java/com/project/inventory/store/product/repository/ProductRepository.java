package com.project.inventory.store.product.repository;

import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.model.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query( value = "SELECT * FROM products WHERE product_is_deleted = 0 AND id =:id", nativeQuery = true )
    Optional<Product> findById( @Param( "id" ) int id );

//    Product findById(int id);

    @Query( value = "SELECT * FROM products WHERE product_is_deleted = 0", nativeQuery = true )
    List<Product> findAllAvailableProducts();

    @Query( value = "SELECT * FROM products p WHERE p.product_is_deleted = 0 AND p.id =:id", nativeQuery = true )
    Optional<Product> findAvailableProductById( @Param( "id" ) int id );

    @Query( value = "SELECT * FROM products as p " +
            "WHERE (p.product_name LIKE concat('%',:query,'%') " +
            "OR p.barcode LIKE concat('%',:query)) AND p.product_is_deleted = 0",
            nativeQuery = true )
    Page<Product> findAll( @Param( "query" ) String query, Pageable pageable );

    @Query( value = " SELECT * FROM products product " +
            "WHERE product.product_name LIKE concat('%',:query,'%') " +
            "OR product.barcode LIKE concat('%',:query)",
            nativeQuery = true )
    Product searchProductByBarcodeOrName( @Param( "query" ) String query );
}
