package com.project.inventory.store.website.carousel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.inventory.store.website.model.StoreInformation;

import javax.persistence.*;

@Entity
@Table(name ="carousel_images")
public class Carousel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="image_name")
    private String imageName;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    @JsonIgnore
    private StoreInformation storeInformation;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName( String imageName ) {
        this.imageName = imageName;
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
