package com.project.inventory.store.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.inventory.store.information.model.StoreInformation;
import com.project.inventory.store.inventory.model.Inventory;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product")
@Transactional
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_name", nullable = false)
    private String name;

//    @NotEmpty(message = "Please provide a name")
    @Column(name = "product_descriptions", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "product_price", nullable = false)
    private double price;

    @Column(name = "created_at", updatable = false, nullable = false, columnDefinition = "DATETIME default current_timestamp")
    @CreationTimestamp
    private Date created;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME default current_timestamp on update current_timestamp")
    @UpdateTimestamp
    private Date updated;

    @Column(name = "product_is_deleted", columnDefinition = "TINYINT(1) default 0", nullable = false)
    private boolean isDeleted = false;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Inventory inventory;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", updatable = false)
    @JsonIgnore
    private StoreInformation storeInformation;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<FileImage> fileImages;

    public Product() {
    }

    public Product(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(int id, String name, String description, double price, boolean isDeleted, Date created, Date updated, StoreInformation storeInformation) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isDeleted = isDeleted;
        this.created = created;
        this.updated = updated;
        this.storeInformation = storeInformation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public StoreInformation getStoreInformation() {
        return storeInformation;
    }

    public void setStoreInformation(StoreInformation storeInformation) {
        this.storeInformation = storeInformation;
    }

    public List<FileImage> getFileImages() {
        return fileImages;
    }

    public void setFileImages( List<FileImage> fileImages ) {
        this.fileImages = fileImages;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !( o instanceof Product ) ) return false;
        Product product = ( Product ) o;
        return getId() == product.getId() && Double.compare( product.getPrice(), getPrice() ) == 0 && isDeleted() == product.isDeleted() && Objects.equals( getName(), product.getName() ) && Objects.equals( getDescription(), product.getDescription() ) && Objects.equals( getCreated(), product.getCreated() ) && Objects.equals( getUpdated(), product.getUpdated() ) && Objects.equals( inventory, product.inventory ) && Objects.equals( getStoreInformation(), product.getStoreInformation() ) && Objects.equals( getFileImages(), product.getFileImages() );
    }

    @Override
    public int hashCode() {
        return Objects.hash( getId(), getName(), getDescription(), getPrice(), getCreated(), getUpdated(), isDeleted(), inventory, getStoreInformation(), getFileImages() );
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", created=" + created +
                ", updated=" + updated +
                ", isDeleted=" + isDeleted +
                ", inventory=" + inventory +
                ", storeInformation=" + storeInformation +
                ", fileImages=" + fileImages +
                '}';
    }
}
