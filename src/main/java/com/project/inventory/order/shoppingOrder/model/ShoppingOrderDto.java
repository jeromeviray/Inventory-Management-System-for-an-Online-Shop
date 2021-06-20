package com.project.inventory.order.shoppingOrder.model;

import com.project.inventory.customer.address.model.CustomerAddressDto;
import com.project.inventory.customer.payment.model.PaymentMethodDto;
import com.project.inventory.order.orderItems.model.OrderItemDto;
import com.project.inventory.permission.model.AccountDto;

import java.util.Date;
import java.util.List;

public class ShoppingOrderDto {

    private int id;
    private OrderStatus orderStatus;
    private double totalAmount;
    private Date orderedAt;
    private Date deliveredAt;
    private AccountDto account;
    private PaymentMethodDto paymentMethod;
    private CustomerAddressDto customerAddress;
    private List<OrderItemDto> orderItem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Date orderedAt) {
        this.orderedAt = orderedAt;
    }

    public Date getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Date deliveredAt) {
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

    public List<OrderItemDto> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItemDto> orderItem) {
        this.orderItem = orderItem;
    }

    @Override
    public String toString() {
        return "ShoppingOrderDto{" +
                "id=" + id +
                ", orderStatus=" + orderStatus +
                ", totalAmount=" + totalAmount +
                ", orderedAt=" + orderedAt +
                ", deliveredAt=" + deliveredAt +
                ", account=" + account +
                ", paymentMethod=" + paymentMethod +
                ", customerAddress=" + customerAddress +
                ", orderItem=" + orderItem +
                '}';
    }
}
