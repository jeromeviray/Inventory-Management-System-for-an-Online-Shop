package com.project.inventory.store.website.agreements.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.inventory.store.website.model.StoreInformation;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "terms_and_conditions")
public class TermsAndCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "content", columnDefinition = "MEDIUMTEXT")
    private String content;

    @JsonFormat(pattern = "dd-MMM-yyyy HH:mm:ss a")
    @CreationTimestamp
    @Column(name = "created_at",  columnDefinition = "DATETIME default CURRENT_TIMESTAMP ")
    private Date createdAt;

    @JsonFormat(pattern = "dd-MMM-yyyy HH:mm:ss a")
    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "DATETIME default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updatedAt;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "store_id")
    private StoreInformation storeInformation;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
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

    public StoreInformation getStoreInformation() {
        return storeInformation;
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
