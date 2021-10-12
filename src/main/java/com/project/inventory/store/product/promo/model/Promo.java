package com.project.inventory.store.product.promo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.inventory.store.product.model.Product;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( name = "product_promos" )
public class Promo {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Column( name = "quantity" )
    private int quantity;

    @Column( name = "sold_quantity" )
    private int soldQuantity;

    @Column( name = "percentage" )
    private int percentage;

    @JsonFormat(pattern = "dd-MMM-yyyy HH:mm:ss")
    @Column( name = "start_date" )
    private Date startDate;

    @JsonFormat(pattern = "dd-MMM-yyyy HH:mm:ss")
    @Column( name = "end_date" )
    private Date endDate;

    @Enumerated(EnumType.STRING)
    @Column( name = "status" )
    private PromoStatus status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", unique = true)
    private Product product;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity( int quantity ) {
        this.quantity = quantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity( int soldQuantity ) {
        this.soldQuantity = soldQuantity;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage( int percentage ) {
        this.percentage = percentage;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate( Date startDate ) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate( Date endDate ) {
        this.endDate = endDate;
    }

    public PromoStatus getStatus() {
        return status;
    }

    public void setStatus( PromoStatus status ) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct( Product product ) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals( Object obj ) {
        return super.equals( obj );
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
