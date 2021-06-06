package com.project.inventory.cart.cartItem.service;

import com.project.inventory.cart.cartItem.model.CartItem;

public interface CartItemService {

    void addCartItem(int productId, CartItem cartItem);
}
