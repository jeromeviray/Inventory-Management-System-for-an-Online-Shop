package com.project.inventory.store.product.category.model;

public interface CategoriesWithTotalProductsDto {
    int getId();
    String getCategoryName();
    int getTotalProducts();
    Object getDeleted();
    String getCreatedAt();
}
