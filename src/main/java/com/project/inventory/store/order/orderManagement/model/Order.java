package com.project.inventory.store.order.orderManagement.model;

import com.project.inventory.customer.address.model.CustomerAddress;
import com.project.inventory.customer.payment.model.PaymentMethod;
import com.project.inventory.store.order.orderItem.model.OrderItem;
import com.project.inventory.common.permission.model.Account;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name = "order_management" )
@Transactional
public class Order {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Column( name = "order_id", nullable = false, unique = true )
    private String orderId;

    @Column( name = "order_status", nullable = false, columnDefinition = "varchar(30)" )
    @Enumerated( EnumType.STRING )
    private OrderStatus orderStatus;

    @Column( name = "total_amount", nullable = false, columnDefinition = "double" )
    private double totalAmount;

    @CreationTimestamp
    @Column( name = "ordered_at", nullable = false, columnDefinition = "DATETIME default current_timestamp" )
    private Date orderedAt;

    @UpdateTimestamp
    @Column( name = "delivered_at", nullable = false, columnDefinition = "DATETIME default current_timestamp on update current_timestamp" )
    private Date deliveredAt;

    @ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinColumn( name = "account_id", nullable = false )
    private Account account;

    @OneToOne( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinColumn( name = "payment_id", nullable = false )
    private PaymentMethod paymentMethod;

    @OneToOne( cascade = CascadeType.MERGE, fetch = FetchType.EAGER )
    @JoinColumn( name = "customer_address_id", nullable = false )
    private CustomerAddress customerAddress;

    @OneToMany( mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    private List<OrderItem> orderItems;


    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId( String orderId ) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus( OrderStatus orderStatus ) {
        this.orderStatus = orderStatus;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount( double totalAmount ) {
        this.totalAmount = totalAmount;
    }

    public Date getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt( Date orderedAt ) {
        this.orderedAt = orderedAt;
    }

    public Date getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt( Date deliveredAt ) {
        this.deliveredAt = deliveredAt;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount( Account account ) {
        this.account = account;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod( PaymentMethod paymentMethod ) {
        this.paymentMethod = paymentMethod;
    }

    public CustomerAddress getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress( CustomerAddress customerAddress ) {
        this.customerAddress = customerAddress;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems( List<OrderItem> orderItems ) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !( o instanceof Order ) ) return false;
        Order order = ( Order ) o;
        return getId() == order.getId() && Double.compare( order.getTotalAmount(), getTotalAmount() ) == 0 && Objects.equals( getOrderId(), order.getOrderId() ) && getOrderStatus() == order.getOrderStatus() && Objects.equals( getOrderedAt(), order.getOrderedAt() ) && Objects.equals( getDeliveredAt(), order.getDeliveredAt() ) && Objects.equals( getAccount(), order.getAccount() ) && Objects.equals( getPaymentMethod(), order.getPaymentMethod() ) && Objects.equals( getCustomerAddress(), order.getCustomerAddress() ) && Objects.equals( getOrderItems(), order.getOrderItems() );
    }

    @Override
    public int hashCode() {
        return Objects.hash( getId(), getOrderId(), getOrderStatus(), getTotalAmount(), getOrderedAt(), getDeliveredAt(), getAccount(), getPaymentMethod(), getCustomerAddress(), getOrderItems() );
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
