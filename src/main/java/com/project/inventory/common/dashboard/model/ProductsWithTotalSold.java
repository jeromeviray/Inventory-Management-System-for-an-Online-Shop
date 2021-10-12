package com.project.inventory.common.dashboard.model;

public interface ProductsWithTotalSold {
    int getId();
    String getProductName();
    double getProductPrice();
    int getBarcode();
    int getTotalSold();
    String getStatus();

}
