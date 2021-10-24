package com.project.inventory.store.cart.repository;

import com.project.inventory.store.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByIdAndAccountId(int cartId, int accountId);
    Cart findByAccountId(int accountId);
    Optional<Cart> findById(int id);

    @Modifying
    @Transactional
    @Query( value = "UPDATE carts SET checkout_expiration = :checkoutExpiration WHERE id =:cartId AND account_id = :accountId", nativeQuery = true )
    void updateCartCheckoutExpiration( @Param("cartId") Integer cartId, @Param( "accountId" ) Integer accountId, @Param( "checkoutExpiration" ) Date checkoutExpiration);
}
