package com.project.inventory.store.product.brand.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.inventory.store.product.model.Product;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "brand_name", nullable = false)
    private String brand;

    @Column(name ="is_deleted", columnDefinition = "TINYINT default 0")
    private boolean isDeleted = false;
    @Column(name = "deleted_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date deletedAt;
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME default current_timestamp")
    private Date createdAt;
    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME default current_timestamp on update current_timestamp")
    private Date updatedAt;
    @OneToMany( mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private List<Product> products;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand( String brand ) {
        this.brand = brand;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted( boolean deleted ) {
        isDeleted = deleted;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt( Date deletedAt ) {
        this.deletedAt = deletedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt( Date createdAt ) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt( Date updatedAt ) {
        this.updatedAt = updatedAt;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts( List<Product> products ) {
        this.products = products;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !( o instanceof Brand ) ) return false;
        Brand brand1 = ( Brand ) o;
        return getId() == brand1.getId() && isDeleted() == brand1.isDeleted() && Objects.equals( getBrand(), brand1.getBrand() ) && Objects.equals( getDeletedAt(), brand1.getDeletedAt() ) && Objects.equals( getCreatedAt(), brand1.getCreatedAt() ) && Objects.equals( getUpdatedAt(), brand1.getUpdatedAt() ) && Objects.equals( getProducts(), brand1.getProducts() );
    }

    @Override
    public int hashCode() {
        return Objects.hash( getId(), getBrand(), isDeleted(), getDeletedAt(), getCreatedAt(), getUpdatedAt(), getProducts() );
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
