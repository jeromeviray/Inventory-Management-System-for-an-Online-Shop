package com.project.inventory.store.order.orderManagement.model;

import com.project.inventory.store.cart.cartItem.model.CartItem;

import java.util.List;

public class PlaceOrder {
    private int customerAddressId;
    private int paymentId;
    private List<CartItem> cartItems;

    public int getCustomerAddressId() {
        return customerAddressId;
    }

    public void setCustomerAddressId(int customerAddressId) {
        this.customerAddressId = customerAddressId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
