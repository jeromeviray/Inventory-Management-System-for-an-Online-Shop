package com.project.inventory.store.cart.cartItem.service;

import com.project.inventory.store.cart.cartItem.model.CartItem;
import com.project.inventory.store.cart.cartItem.model.CartItemDto;

import java.text.ParseException;
import java.util.List;

public interface CartItemService {

    void addCartItem( int productId ) throws ParseException;

    CartItem increaseQuantity( int productId ) throws ParseException;

    CartItem decreaseQuantity( int productId );

    CartItem getCartItemByCartIdAndProductId( int cartId, int productId );

    CartItem getCartItemById( int id );

    void removeItems( List<CartItemDto> cartItems );

    void removeItem( int itemId );

    void deleteById( int id );

    CartItemDto getCartItem( int id );
}
