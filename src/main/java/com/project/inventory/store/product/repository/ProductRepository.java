package com.project.inventory.store.product.repository;

import com.project.inventory.store.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query( value = "SELECT * FROM products WHERE product_is_deleted = 0 AND id =:id", nativeQuery = true )
    Optional<Product> findById( @Param( "id" ) int id );

    @Query( value = "SELECT * FROM products WHERE product_is_deleted = 0", nativeQuery = true )
    List<Product> findAllAvailableProducts();

    @Query( value = "SELECT * FROM products p WHERE p.product_is_deleted = 0 AND p.id =:id", nativeQuery = true )
    Optional<Product> findAvailableProductById( @Param( "id" ) int id );

    @Query( value = "SELECT * FROM products as p " +
            "LEFT JOIN product_promos promo " +
            "ON p.id = promo.product_id " +
            "WHERE (p.product_name LIKE concat('%',:query,'%') " +
            "OR p.barcode LIKE concat('%',:query)) AND p.product_is_deleted = 0",
            nativeQuery = true )
    Page<Product> findAll( @Param( "query" ) String query, Pageable pageable );

    @Query( value = "SELECT * " +
            "FROM products p " +
            "LEFT JOIN inventories i " +
            "ON p.id = i.product_id " +
            "WHERE (p.product_is_deleted = 0 AND i.inventory_status =:status) AND (p.product_name LIKE concat('%',:query,'%') " +
            "OR p.barcode LIKE concat('%',:query))",
            nativeQuery = true )
    Page<Product> findAllProductsByStatus( @Param( "query" ) String query, @Param( "status" ) String status, Pageable pageable );

    @Query( value = "SELECT * " +
            "FROM  products product " +
            "LEFT JOIN product_promos promo " +
            "ON product.id = promo.product_id " +
            "JOIN product_categories as category " +
            "ON  product.category_id = category.id  " +
            "LEFT JOIN brands brand " +
            "ON product.brand_id = brand.id " +
            "WHERE product.product_is_deleted = 0 " +
            "AND category.category_name =:categoryName " +
            "AND (product.product_name LIKE concat('%', :query, '%') OR brand.brand_name LIKE concat('%', :query, '%'))",
            countQuery = "SELECT count(*) FROM products product WHERE product.product_is_deleted = 0",
            nativeQuery = true )
    Page<Product> findAllProductsByCategoryName( @Param( "categoryName" ) String categoryName, @Param( "query" ) String query, Pageable pageable );

    @Query( value = "SELECT *  from products product " +
            "JOIN product_promos promo " +
            "ON product.id = promo.product_id " +
            "WHERE product.product_name LIKE concat('%', :query, '%') ",
            nativeQuery = true )
    Page<Product> findAllProductsWithPromo( @Param( "query" ) String query, Pageable pageable );

    @Query( value = "SELECT *  from products product " +
            "JOIN product_promos promo " +
            "ON product.id = promo.product_id " +
            "WHERE promo.status =:status " +
            "AND product.product_name LIKE concat('%', :query, '%') ",
            nativeQuery = true )
    Page<Product> findAllProductsWithPromoAndStatus( @Param( "query" ) String query, @Param( "status" ) String status, Pageable pageable );

    @Query( value = "SELECT product.*, COUNT(product.id) as totalOrdered FROM order_items as item " +
            "JOIN orders  ON orders.id = item.order_id " +
            "JOIN products as product ON product.id = item.product_id " +
            "WHERE orders.order_status IN ('delivered', 'shipped', 'confirmed', 'pending') AND product.product_is_deleted = 0 " +
            "GROUP BY product.id " +
            "ORDER BY count(product.id) ",
            nativeQuery = true
    )
    Page<Product> getPopularProducts(Pageable pageable);
}

