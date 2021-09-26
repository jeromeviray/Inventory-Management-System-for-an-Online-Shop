package com.project.inventory.store.product.category.model;

import com.project.inventory.store.inventory.model.GetProductDto;

import java.util.List;

public class CategoryDto {

    private int id;
    private String name;
    private List<GetProductDto> products;

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

    public List<GetProductDto> getProducts() {
        return products;
    }

    public void setProducts( List<GetProductDto> products ) {
        this.products = products;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
