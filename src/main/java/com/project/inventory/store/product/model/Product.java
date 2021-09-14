package com.project.inventory.store.product.model;

import com.project.inventory.store.information.branch.model.Branch;
import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.product.brand.model.Brand;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name = "product" )
@Transactional
public class Product implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Column( name = "product_name", nullable = false )
    private String name;

    //    @NotEmpty(message = "Please provide a name")
    @Column( name = "product_descriptions", nullable = false, columnDefinition = "TEXT" )
    private String description;

    @Column( name = "product_price", nullable = false )
    private double price;

    @Column( name = "barcode", nullable = false )
    private int barcode;

    @Column( name = "created_at", updatable = false, nullable = false, columnDefinition = "DATETIME default current_timestamp" )
    @CreationTimestamp
    private Date created;

    @Column( name = "updated_at", nullable = false, columnDefinition = "DATETIME default current_timestamp on update current_timestamp" )
    @UpdateTimestamp
    private Date updated;

    @Column( name = "product_is_deleted", columnDefinition = "TINYINT(1) default 0", nullable = false )
    private boolean isDeleted = false;

    @OneToOne( mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private Inventory inventory;

    @ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinColumn( name = "branch_id" )
    private Branch branch;

    @OneToMany( mappedBy = "product", fetch = FetchType.EAGER )
    private List<FileImage> fileImages;

    @ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinColumn( name = "brand_id" )
    private Brand brand;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice( double price ) {
        this.price = price;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode( int barcode ) {
        this.barcode = barcode;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated( Date created ) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated( Date updated ) {
        this.updated = updated;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted( boolean deleted ) {
        isDeleted = deleted;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory( Inventory inventory ) {
        this.inventory = inventory;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch( Branch branch ) {
        this.branch = branch;
    }

    public List<FileImage> getFileImages() {
        return fileImages;
    }

    public void setFileImages( List<FileImage> fileImages ) {
        this.fileImages = fileImages;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand( Brand brand ) {
        this.brand = brand;
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
