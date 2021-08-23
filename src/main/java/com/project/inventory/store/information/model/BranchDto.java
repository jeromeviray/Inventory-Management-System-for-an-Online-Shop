package com.project.inventory.store.information.model;

import com.project.inventory.store.product.model.ProductDto;

import java.util.List;

public class BranchDto {
    private String branch;
    private List<ProductDto> products;

    public String getBranch() {
        return branch;
    }

    public void setBranch( String branch ) {
        this.branch = branch;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts( List<ProductDto> products ) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "StoreInformationDto{" +
                "branch='" + branch + '\'' +
                ", products=" + products +
                '}';
    }
}
