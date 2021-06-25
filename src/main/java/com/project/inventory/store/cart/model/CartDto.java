package com.project.inventory.store.cart.model;

import com.project.inventory.store.cart.cartItem.model.CartItemDto;

import java.util.List;

public class CartDto {
    private int cartId;
    private int accountId;
    private List<CartItemDto> cartItemsDto;


    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public List<CartItemDto> getCartItemsDto() {
        return cartItemsDto;
    }

    public void setCartItemsDto(List<CartItemDto> cartItemsDto) {
        this.cartItemsDto = cartItemsDto;
    }
}
