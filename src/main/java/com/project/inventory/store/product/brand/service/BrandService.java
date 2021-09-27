package com.project.inventory.store.product.brand.service;

import com.project.inventory.store.product.brand.model.Brand;
import com.project.inventory.store.product.brand.model.GetBrandsWithTotalProducts;

import java.util.List;

public interface BrandService {
    void saveBrand( Brand brand );

    List<Brand> getBrands();

    Brand getBrand( int id );

    Brand updateBrand( int id, Brand brand );

    void deleteBrand( int id );

    List<GetBrandsWithTotalProducts> getBrandsWithTotalProducts();

}
