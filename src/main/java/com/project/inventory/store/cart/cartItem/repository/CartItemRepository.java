package com.project.inventory.store.cart.cartItem.repository;

import com.project.inventory.store.cart.cartItem.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
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
    @Query(value = "delete from cart_item where id =:id", nativeQuery = true)
    void deleteById(@Param("id") int id);

}
