package com.project.inventory.store.information.branch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.inventory.store.product.model.Product;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table( name = "branch" )
public class Branch implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Column( name = "branch", columnDefinition = "varchar(100)", nullable = false )
    private String branch;

    @Column( name = "is_deleted", columnDefinition = "TINYINT default 0")
    private boolean isDeleted = false;

    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME default current_timestamp")
    private Date createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME default current_timestamp on update current_timestamp")
    private Date updatedAt;

    @Column(name = "deleted_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date deletedAt;

    @OneToMany( mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private List<Product> products;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch( String branch ) {
        this.branch = branch;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted( boolean deleted ) {
        isDeleted = deleted;
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

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt( Date deletedAt ) {
        this.deletedAt = deletedAt;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts( List<Product> products ) {
        this.products = products;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
