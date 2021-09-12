package com.project.inventory.store.product.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "file_image")
public class FileImage implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column( name = "file_name" )
    private String fileName;

    @Column( name = "size" )
    private Long size;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    public FileImage() {
    }

    public FileImage(String fileName) {
        this.fileName = fileName;
    }

    public FileImage(String fileName, Long size) {
        this.fileName = fileName;
        this.size = size;
    }

    public FileImage(int id, String fileName, Long size) {
        this.id = id;
        this.fileName = fileName;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "FileImage{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", size=" + size +
                ", product=" + product +

                '}';
    }
}

