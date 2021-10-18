package com.project.inventory.store.product.brand.service.impl;

import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.product.brand.model.Brand;
import com.project.inventory.store.product.brand.model.BrandsWithTotalProductsDto;
import com.project.inventory.store.product.brand.repository.BrandRepository;
import com.project.inventory.store.product.brand.service.BrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    Logger logger = LoggerFactory.getLogger( BrandServiceImpl.class );

    @Autowired
    private BrandRepository brandRepository;


    @Override
    public void saveBrand( Brand brand ) {
        logger.info( "Saving brand. {}", brand.getBrand() );
        brandRepository.save( brand );
    }

    @Override
    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrand( int id ) {
        logger.info( "Find Brand {}", id );
        return brandRepository.findByIdAndIsDeleted( id )
                .orElseThrow( () -> new NotFoundException( "Brand not Found." ) );
    }

    @Override
    public Brand getBrandByBrandName( String brandName ) {
        return brandRepository.findByBrandName( brandName );
    }

    @Override
    public Brand updateBrand( int id, Brand brand ) {
        Brand savedBrand = getBrand( id );
        savedBrand.setBrand( brand.getBrand() );
        return brandRepository.save( savedBrand );
    }

    @Override
    public void deleteBrand( int id ) {
        Brand brand = getBrand( id );
        brand.setDeleted( true );
        saveBrand( brand );
    }

    @Override
    public Page<BrandsWithTotalProductsDto> getBrandsWithTotalProducts( String query, Pageable pageable ) {
//        return brandRepository.findAllBrandAndProduct();
        try {
            Page<BrandsWithTotalProductsDto> brands = brandRepository.findAll( query, pageable );

            List<BrandsWithTotalProductsDto> brandRecordsByPage = new ArrayList<>();
            for( BrandsWithTotalProductsDto brandsWithTotalProductsDto : brands.getContent() ) {
                brandRecordsByPage.add( brandsWithTotalProductsDto );
            }
            return new PageImpl<>( brandRecordsByPage, pageable, brands.getTotalElements() );
        } catch( Exception e ) {
            throw e;
        }
    }
}
