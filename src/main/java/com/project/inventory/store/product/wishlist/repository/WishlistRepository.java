package com.project.inventory.store.product.wishlist.repository;

import com.project.inventory.store.product.wishlist.model.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    @Query( value = "SELECT w.* " +
            "FROM customer_product_wishlist as w " +
            "JOIN products as product ON w.product_id = product.id " +
            "WHERE product.product_name LIKE concat('%',:query,'%') AND account_id = :accountId AND product.product_is_deleted = 0 ",
            countQuery = "SELECT count(*) FROM customer_product_wishlist  JOIN products as product ON w.product_id = product.id WHERE account_id = :accountId AND product.product_is_deleted = 0",
            nativeQuery = true )
    Page<Wishlist> findWishlist(@Param( "accountId" ) Integer account_id, String query, Pageable pageable);

    @Query(value = "SELECT w.* " +
            "FROM customer_product_wishlist as w " +
            "JOIN products as product ON w.product_id = product.id " +
            "WHERE product.id = :productId AND account_id = :accountId AND product.product_is_deleted = 0 ",
            nativeQuery = true)
    Wishlist findWishlistByProductId(@Param( "accountId" ) Integer account_id, @Param("productId") Integer productId);
}
