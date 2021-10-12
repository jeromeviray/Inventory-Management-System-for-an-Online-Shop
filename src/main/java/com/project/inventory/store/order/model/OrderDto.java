package com.project.inventory.store.order.model;

import com.project.inventory.customer.address.model.CustomerAddressDto;
import com.project.inventory.customer.payment.model.PaymentMethodDto;
import com.project.inventory.store.order.orderItem.model.OrderItemDto;
import com.project.inventory.common.permission.model.AccountDto;
import com.project.inventory.store.product.comment.model.Comment;

import java.util.List;

public class OrderDto {

    private Integer id;
    private String orderId;
    private OrderStatus orderStatus;
    private double totalAmount;
    private String orderedAt;
    private String deliveredAt;
    private AccountDto account;
    private PaymentMethodDto paymentMethod;
    private CustomerAddressDto customerAddress;
    private List<OrderItemDto> orderItems;
    private List<Comment> comments;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId( String orderId ) {
        this.orderId = orderId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments( List<Comment> comments ) {
        this.comments = comments;
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