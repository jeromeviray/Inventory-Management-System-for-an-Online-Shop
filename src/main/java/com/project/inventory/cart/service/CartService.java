package com.project.inventory.cart.service;

import com.project.inventory.cart.model.Cart;

public interface CartService {
    Cart getCartByCartIdAndAccountId(int cartId, int AccountId);
    Cart createCart(int accountId);
    Cart getCartByAccountId(int accountId);
}
