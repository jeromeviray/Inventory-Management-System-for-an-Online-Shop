package com.project.inventory.store.product.brand.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface GetBrandsWithTotalProducts {

    int getId();
    String getBrandName();
    int getTotalProducts();
    String getCreatedAt();
    Object getIsDeleted();

}
