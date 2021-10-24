package com.project.inventory.store.order.model;

import com.project.inventory.store.cart.cartItem.model.CartItemDto;

import java.util.List;

public class CheckoutOrderValidate {
    private Integer cartId;
    private Integer accountId;
    private List<CartItemDto> items;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId( Integer cartId ) {
        this.cartId = cartId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId( Integer accountId ) {
        this.accountId = accountId;
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems( List<CartItemDto> items ) {
        this.items = items;
    }
}
