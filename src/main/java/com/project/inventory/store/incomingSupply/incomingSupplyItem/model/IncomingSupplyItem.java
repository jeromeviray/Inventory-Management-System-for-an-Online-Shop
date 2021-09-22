package com.project.inventory.store.incomingSupply.incomingSupplyItem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.inventory.store.incomingSupply.model.IncomingSupply;
import com.project.inventory.store.product.model.Product;

import javax.persistence.*;

@Entity
@Table(name = "incoming_supply_items")
public class IncomingSupplyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "number_received")
    private int numberReceived;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "incoming_supply_id", nullable = false, updatable = false)
    public IncomingSupply incomingSupply;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public int getNumberReceived() {
        return numberReceived;
    }

    public void setNumberReceived( int numberReceived ) {
        this.numberReceived = numberReceived;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct( Product product ) {
        this.product = product;
    }

    public IncomingSupply getIncomingSupply() {
        return incomingSupply;
    }

    public void setIncomingSupply( IncomingSupply incomingSupply ) {
        this.incomingSupply = incomingSupply;
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
