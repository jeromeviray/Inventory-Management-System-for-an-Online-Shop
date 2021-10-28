package com.project.inventory.store.order.model;

import com.project.inventory.store.cart.cartItem.model.CartItemDto;

import java.util.List;

public class PlaceOrder {
    private int customerAddressId;
    private int paymentMethodId;
    private List<CartItemDto> cartItems;
    private int shippingFeeId;
    private int cartId;

    public int getCustomerAddressId() {
        return customerAddressId;
    }

    public void setCustomerAddressId(int customerAddressId) {
        this.customerAddressId = customerAddressId;
    }

    public int getPaymentId() {
        return paymentMethodId;
    }

    public void setPaymentId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public List<CartItemDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems( List<CartItemDto> cartItems ) {
        this.cartItems = cartItems;
    }


    public int getShippingFeeId() {
        return shippingFeeId;
    }

    public void setShippingFeeId( int shippingFeeId ) {
        this.shippingFeeId = shippingFeeId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId( int cartId ) {
        this.cartId = cartId;
    }
}
