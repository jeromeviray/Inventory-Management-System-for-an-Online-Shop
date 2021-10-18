package com.project.inventory.common.dashboard.model;

public interface ProductsWithTotalSold {
    Integer getId();
    String getProductName();
    Double getProductPrice();
    Integer getBarcode();
    Integer getTotalSold();
    String getStatus();
}
