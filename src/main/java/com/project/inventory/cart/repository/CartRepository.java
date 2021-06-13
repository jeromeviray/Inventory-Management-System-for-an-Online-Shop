package com.project.inventory.cart.repository;

import com.project.inventory.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByIdAndAccountId(int cartId, int accountId);
    Cart findByAccountId(int accountId);
    Optional<Cart> findById(int id);
}
