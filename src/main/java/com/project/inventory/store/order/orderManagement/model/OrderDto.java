package com.project.inventory.store.order.orderManagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.inventory.customer.address.model.CustomerAddressDto;
import com.project.inventory.customer.payment.model.PaymentMethodDto;
import com.project.inventory.store.order.orderItem.model.OrderItemDto;
import com.project.inventory.common.permission.model.AccountDto;

import java.util.Date;
import java.util.List;

public class OrderDto {

    private String orderId;
    private OrderStatus orderStatus;
    private double totalAmount;
    private String orderedAt;
    private String deliveredAt;
    private AccountDto account;
    private PaymentMethodDto paymentMethod;
    private CustomerAddressDto customerAddress;
    private List<OrderItemDto> orderItems;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId( String orderId ) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(String orderedAt) {
        this.orderedAt = orderedAt;
    }

    public String getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(String deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }

    public PaymentMethodDto getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodDto paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public CustomerAddressDto getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(CustomerAddressDto customerAddress) {
        this.customerAddress = customerAddress;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems( List<OrderItemDto> orderItems ) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
