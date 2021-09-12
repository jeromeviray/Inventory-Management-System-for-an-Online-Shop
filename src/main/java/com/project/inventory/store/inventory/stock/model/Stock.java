package com.project.inventory.store.inventory.stock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.inventory.store.inventory.model.Inventory;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( name = "stock" )
public class Stock {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Column( name = "stock_id", updatable = false, nullable = false )
    private String stockId;

    @Column( name = "stock" )
    private int stock;

    @CreationTimestamp
    @Column( name = "added_at" )
    private Date added;

    @UpdateTimestamp
    @Column( name = "updated_at" )
    private Date updated;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn( name = "inventory_id" )
    private Inventory inventory;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId( String stockId ) {
        this.stockId = stockId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock( int stock ) {
        this.stock = stock;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded( Date added ) {
        this.added = added;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated( Date updated ) {
        this.updated = updated;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory( Inventory inventory ) {
        this.inventory = inventory;
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
