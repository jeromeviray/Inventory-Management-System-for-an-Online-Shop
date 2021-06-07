package com.project.inventory.cart.cartItem.repository;

import com.project.inventory.cart.cartItem.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    CartItem findById(int id);
    CartItem findByCartIdAndProductId(int cartId, int productId);
}
