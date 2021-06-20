package com.project.inventory.cart.model;

import com.project.inventory.cart.cartItem.model.CartItem;
import com.project.inventory.cart.cartItem.model.CartItemDto;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;

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
