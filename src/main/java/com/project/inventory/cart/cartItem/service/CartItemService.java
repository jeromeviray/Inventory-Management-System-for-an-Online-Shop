package com.project.inventory.cart.cartItem.service;

import com.project.inventory.cart.cartItem.model.CartItem;

import java.util.List;

public interface CartItemService {

    void addCartItem(int productId, CartItem cartItem);
    int increaseQuantity(int accountId, int productId);
    int decreaseQuantity(int accountId, int productId);
    CartItem getCartItemByCartIdAndProductId(int cartId, int productId);
    void removeItems(List<CartItem> cartItems);
    void removeItem(int itemId);
    void deleteById(int id);
}
