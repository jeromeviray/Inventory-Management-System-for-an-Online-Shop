package com.project.inventory.cart.repository;

import com.project.inventory.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findByIdAndAccountId(int cartId, int accountId);
    Cart findByAccountId(int accountId);
}
