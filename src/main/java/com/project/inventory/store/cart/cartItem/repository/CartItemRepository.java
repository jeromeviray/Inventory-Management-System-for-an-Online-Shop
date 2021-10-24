package com.project.inventory.store.cart.cartItem.repository;

import com.project.inventory.store.cart.cartItem.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findById(int id);
    CartItem findByCartIdAndProductId(int cartId, int productId);

//    @Query(value = "SELECT * FROM cart_item item WHERE item.cart_item_id = :id AND item.cart_id = :cartId",
//            nativeQuery = true)
//    CartItem findByIdAndCartId(@Param("id") int id, @Param("cartId") int cartId);
    Optional<CartItem> findByIdAndCartId(int id, int cartId);

    @Modifying
    @Query(value = "delete from cart_items where id =:id", nativeQuery = true)
    void deleteById(@Param("id") int id);

    @Modifying
    @Transactional
    @Query( value = "UPDATE cart_items SET checkout_expiration = :checkoutExpiration WHERE cart_id =:cartId AND product_id = :productId", nativeQuery = true )
    void updateCartCheckoutExpiration( @Param("cartId") Integer cartId, @Param( "productId" ) Integer productId, @Param( "checkoutExpiration" ) Date checkoutExpiration);

    @Query(value = "SELECT SUM(quantity) FROM cart_items as item JOIN carts as cart ON cart.id = item.cart_id " +
            " WHERE product_id = :productId AND :checkoutExpiration <= item.checkout_expiration AND cart.account_id != :accountId",
        nativeQuery = true)
    Integer getLockProducts(@Param("productId") Integer productId, @Param("accountId") Integer accountId, Date checkoutExpiration);
}
