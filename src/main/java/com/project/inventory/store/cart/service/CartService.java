package com.project.inventory.store.cart.service;

import com.project.inventory.store.cart.cartItem.model.CartItem;
import com.project.inventory.store.cart.model.Cart;
import com.project.inventory.store.cart.model.CartDto;

import java.util.List;

public interface CartService {
    Cart getCartByCartIdAndAccountId( int cartId, int AccountId );

    Cart createCart( int accountId );

    Cart getCartByAccountId( int accountId );

    CartDto getCartByAccountIdDto( int accountId );

    Cart getCartById( int id );

    List<CartItem> getCartProducts( int cartId );

    CartDto convertEntityToDto(Cart cart);

    Cart convertDtoToEntity(CartDto cartDto);
}
