package com.project.inventory.store.order.orderManagement.model;

import com.project.inventory.store.cart.cartItem.model.CartItem;

import java.util.List;

public class PlaceOrder {
    private int customerAddressId;
    private int paymentMethodId;
    private List<CartItem> cartItems;

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

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
