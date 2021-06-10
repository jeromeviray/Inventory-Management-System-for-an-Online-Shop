package com.project.inventory.cart.service;

import com.project.inventory.cart.cartItem.model.CartItem;
import com.project.inventory.cart.model.Cart;
import com.project.inventory.product.model.Product;

import java.util.List;

public interface CartService {
    Cart getCartByCartIdAndAccountId(int cartId, int AccountId);
    Cart createCart(int accountId);
    Cart getCartByAccountId(int accountId);
    Cart getCartById(int id);
    List<CartItem> getCartProducts(int cartId);
}
