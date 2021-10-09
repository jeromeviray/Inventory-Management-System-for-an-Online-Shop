package com.project.inventory.store.product.comment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.inventory.store.product.model.Product;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product_comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME default current_timestamp")
    private Date createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME default current_timestamp on update current_timestamp")
    private Date updatedAt;

    @OneToOne(fetch = FetchType.LAZY )
    @JoinColumn( name = "product_id" )
    @JsonIgnoreProperties({"inventory", "hibernateLazyInitializer"})
    private Product product;

    @Column(name = "account_id", nullable = false)
    @JsonIgnore
    private Integer accountId;

    @Column(name = "message")
    private String message;

    @Column(name = "published")
    @JsonIgnore
    private Boolean published;

    @Column(name = "is_anonymous", columnDefinition = "boolean default false")
    private Boolean anonymous;

    @Column(name = "rating")
    private Integer rating;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
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

    public Product getProduct() {
        return this.product;
    }

    public void setProduct( Product product ) {
        this.product = product;
    }

    public void setAccountId( Integer accountId ) {
        this.accountId = accountId;
    }

    public Integer getAccountId() {
        return this.accountId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished( Boolean published ) {
        this.published = published;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous( Boolean anonymous ) {
        this.anonymous = anonymous;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating( Integer rating ) {
        this.rating = rating;
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
