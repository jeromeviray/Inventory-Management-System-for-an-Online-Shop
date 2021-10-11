package com.project.inventory.store.order.model;

import com.project.inventory.common.HttpResponse;

public class OrderResponse extends HttpResponse {
    private String checkoutUrl;
    private String paymentMethod;
    private Order order;

    public String getRedirectUrl() {
        return checkoutUrl;
    }

    public void setRedirectUrl( String redirectUrl ) {
        this.checkoutUrl = redirectUrl;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod( String paymentMethod ) {
        this.paymentMethod = paymentMethod;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder( Order order ) {
        this.order = order;
    }
}
