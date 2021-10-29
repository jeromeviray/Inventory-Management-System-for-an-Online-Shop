package com.project.inventory.store.product.brand.service;

import com.project.inventory.store.product.brand.model.Brand;
import com.project.inventory.store.product.brand.model.BrandsWithTotalProductsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BrandService {
    void saveBrand( Brand brand );

    Brand getBrandByBrandName(String brandName);

    List<BrandsWithTotalProductsDto> getBrands();

    Brand getBrand( int id );

    Brand updateBrand( int id, Brand brand );

    void deleteBrand( int id );

    Page<BrandsWithTotalProductsDto> getBrandsWithTotalProducts( String query, Pageable pageable );

}
