package com.project.inventory.store.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.inventory.store.incomingSupply.incomingSupplyItem.model.IncomingSupplyItem;
import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.product.brand.model.Brand;
import com.project.inventory.store.product.category.model.Category;
import com.project.inventory.store.product.promo.model.Promo;
import com.project.inventory.store.product.wishlist.model.Wishlist;
import com.project.inventory.store.website.model.StoreInformation;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table( name = "products" )
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

    @Column( name = "barcode",unique = true, updatable = false, columnDefinition = "VARCHAR(30)"  )
    private String barcode;

    @Column( name = "created_at", updatable = false, nullable = false, columnDefinition = "DATETIME default current_timestamp" )
    @CreationTimestamp
    private Date created;

    @Column( name = "updated_at", nullable = false, columnDefinition = "DATETIME default current_timestamp on update current_timestamp" )
    @UpdateTimestamp
    private Date updated;

    @Column( name = "product_is_deleted", columnDefinition = "TINYINT(1) default 0", nullable = false )
    private boolean isDeleted = false;

    @OneToOne( mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JsonIgnoreProperties({"product", "stock", "hibernateLazyInitializer"})
    private Inventory inventory;

    @OneToMany( mappedBy = "product", fetch = FetchType.EAGER )
    @JsonIgnoreProperties({"product", "hibernateLazyInitializer"})
    private List<FileImage> fileImages;

    @ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinColumn( name = "brand_id" )
    @JsonIgnoreProperties({"product", "hibernateLazyInitializer"})
    private Brand brand;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<IncomingSupplyItem> incomingSupplyItems;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Wishlist> wishlist;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn( name = "category_id" )
    @JsonIgnoreProperties({"product", "hibernateLazyInitializer"})
    private Category category;

    @OneToOne(mappedBy = "product", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"product", "hibernateLazyInitializer"})
    private Promo promo;

    private Integer rating;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private StoreInformation storeInformation;

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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode( String barcode ) {
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

    public List<IncomingSupplyItem> getIncomingSupplyItems() {
        return incomingSupplyItems;
    }

    public void setIncomingSupplyItems( List<IncomingSupplyItem> incomingSupplyItems ) {
        this.incomingSupplyItems = incomingSupplyItems;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory( Category category ) {
        this.category = category;
    }

    public List<Wishlist> getWishlist() {
        return wishlist;
    }

    public void setWishlist( List<Wishlist> wishlist ) {
        this.wishlist = wishlist;
    }

    public Promo getPromo() {
        return promo;
    }

    public void setPromo( Promo promo ) {
        this.promo = promo;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating( Integer rating ) {
        this.rating = rating;
    }

    public void setStoreInformation( StoreInformation storeInformation ) {
        this.storeInformation = storeInformation;
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
