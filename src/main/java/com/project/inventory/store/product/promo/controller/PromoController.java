package com.project.inventory.store.product.promo.controller;

import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.model.ProductDto;
import com.project.inventory.store.product.promo.model.Promo;
import com.project.inventory.store.product.promo.model.PromoDto;
import com.project.inventory.store.product.promo.model.PromoRequest;
import com.project.inventory.store.product.promo.model.PromoStatus;
import com.project.inventory.store.product.promo.service.PromoService;
import com.project.inventory.store.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping( value = "/api/v1/promos" )
public class PromoController {

    @Autowired
    private PromoService promoService;
    @Autowired
    private ProductService productService;


    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = "/save", method = RequestMethod.POST )
    public ResponseEntity<?> savePromo( @RequestBody PromoRequest promoRequest ) throws Exception {
        promoService.savePromo( promoRequest );
        return new ResponseEntity<>( HttpStatus.OK );

    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<?> getPromos() throws ParseException {
        return new ResponseEntity<>( promoService.getPromos(), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = "/delete/{id}", method = RequestMethod.DELETE )
    public ResponseEntity<?> deletePromo( @PathVariable int id ) throws Exception {
        promoService.deletePromo( id );
        return new ResponseEntity<>( HttpStatus.OK );
    }
    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = "/update/{id}", method = RequestMethod.PUT )
    public ResponseEntity<?> updatePromo( @PathVariable int id, @RequestBody PromoRequest promoRequest ) throws Exception {
        Promo promo = new Promo();
        SimpleDateFormat pattern = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss" );
        promo.setPercentage( promoRequest.getPercentage() );
        promo.setProduct( productService.getProductById( promoRequest.getProductId() ) );
        promo.setQuantity( promoRequest.getQuantity() );
        promo.setSoldQuantity( promoRequest.getSoldQuantity() );
        promo.setStartDate( pattern.parse( promoRequest.getStartDate() ) );
        promo.setEndDate( pattern.parse( promoRequest.getEndDate() ) );
        promo.setStatus( promoService.checkSchedulePromo( promo ) );

        PromoDto promoDto = promoService.convertEntityToDto( promoService.updatePromo( id, promo ) );
        return new ResponseEntity<>(promoDto, HttpStatus.OK );
    }
}
