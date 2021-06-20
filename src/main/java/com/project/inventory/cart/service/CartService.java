package com.project.inventory.cart.service;

import com.project.inventory.cart.cartItem.model.CartItem;
import com.project.inventory.cart.model.Cart;
import com.project.inventory.cart.model.CartDto;
import com.project.inventory.product.model.Product;

import java.util.List;
import java.util.Optional;

public interface CartService {
    Cart getCartByCartIdAndAccountId(int cartId, int AccountId);
    Cart createCart(int accountId);
    Cart getCartByAccountId(int accountId);
    CartDto getCartByAccountIdDto(int accountId);
    Cart getCartById(int id);
    List<CartItem> getCartProducts(int cartId);
}
