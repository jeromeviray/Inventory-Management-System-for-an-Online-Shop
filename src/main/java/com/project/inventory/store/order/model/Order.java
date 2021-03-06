package com.project.inventory.store.order.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.inventory.customer.address.model.CustomerAddress;
import com.project.inventory.customer.payment.model.PaymentMethod;
import com.project.inventory.store.order.orderItem.model.OrderItem;
import com.project.inventory.common.permission.model.Account;
import com.project.inventory.store.product.comment.model.Comment;
import com.project.inventory.store.shipping.model.ShippingFee;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name = "orders" )
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="shipping_fee_id")
    private ShippingFee shippingFee;

    @Column( name = "total_amount", nullable = false, columnDefinition = "double" )
    private double totalAmount;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss a")
    @CreationTimestamp
    @Column( name = "ordered_at", nullable = false, columnDefinition = "DATETIME default current_timestamp" )
    private Date orderedAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss a")
    @Column( name = "delivered_at", columnDefinition = "DATETIME default NULL" )
    private Date deliveredAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss a")
    @Column( name = "paid_at",  columnDefinition = "DATETIME default null")
    private Date paid_at;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss a")
    @Column( name = "refund_at",  columnDefinition = "DATETIME default null")
    private Date refundAt;

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

    @OneToMany( mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JsonIgnoreProperties({"order"})
    private List<Comment> comments;

    @Column( name = "payment_status", columnDefinition = "INTEGER DEFAULT 0" )
    Integer paymentStatus;

    @Column( name = "tracking_url", columnDefinition = "VARCHAR(45) DEFAULT NULL" )
    String trackingUrl;

    @Column( name = "tracking_number", columnDefinition = "VARCHAR(45) DEFAULT NULL" )
    String trackingNumber;

    @Column( name = "external_reference", columnDefinition = "VARCHAR(255) DEFAULT NULL" )
    String externalReference;

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

    public ShippingFee getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee( ShippingFee shippingFee ) {
        this.shippingFee = shippingFee;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments( List<Comment> comments ) {
        this.comments = comments;
    }

    public Date getPaid_at() {
        return paid_at;
    }

    public void setPaid_at( Date paid_at ) {
        this.paid_at = paid_at;
    }

    public Date getRefundAt() {
        return refundAt;
    }

    public void setRefundAt( Date refundAt ) {
        this.refundAt = refundAt;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus( Integer paymentStatus ) {
        this.paymentStatus = paymentStatus;
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    public void setTrackingUrl( String trackingUrl ) {
        this.trackingUrl = trackingUrl;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber( String trackingNumber ) {
        this.trackingNumber = trackingNumber;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference( String externalReference ) {
        this.externalReference = externalReference;
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
