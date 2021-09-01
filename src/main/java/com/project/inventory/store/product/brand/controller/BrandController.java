package com.project.inventory.store.product.brand.controller;

import com.project.inventory.store.product.brand.model.Brand;
import com.project.inventory.store.product.brand.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( value = "/api/v1/brands" )
public class BrandController {
    @Autowired
    private BrandService brandService;

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = "/save", method = RequestMethod.POST )
    public ResponseEntity<?> saveBrand( @RequestBody Brand brand ) {
        brandService.saveBrand( brand );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<?> getBrands() {
        return new ResponseEntity<>( brandService.getBrands(), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public ResponseEntity<?> getBrand( @PathVariable int id ) {
        return new ResponseEntity( brandService.getBrand( id ), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = "/update/{id}", method = RequestMethod.PUT )
    public ResponseEntity<?> updateBrand( @PathVariable int id, @RequestBody Brand brand ) {
        return new ResponseEntity<>( brandService.updateBrand( id, brand ), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/delete/{id}", method = RequestMethod.DELETE )
    public ResponseEntity<?> deleteBrand( @PathVariable int id ) {
        brandService.deleteBrand( id );
        return new ResponseEntity<>( HttpStatus.OK );
    }
}
