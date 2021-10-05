package com.project.inventory.store.product.promo.controller;

import com.project.inventory.store.product.promo.model.Promo;
import com.project.inventory.store.product.promo.model.PromoRequest;
import com.project.inventory.store.product.promo.model.PromoStatus;
import com.project.inventory.store.product.promo.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping( value = "api/v1/promo" )
public class PromoController {

    @Autowired
    private PromoService promoService;


    @RequestMapping( value = "/save", method = RequestMethod.POST )
    public ResponseEntity<?> savePromo( @RequestBody PromoRequest promoRequest ) throws ParseException {
        SimpleDateFormat pattern = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss" );
//        Date current =
//        if()
        Promo promo = new Promo();
        promo.setPercentage( promoRequest.getPercentage() );
        promo.setQuantity( promoRequest.getQuantity() );
        promo.setStartDate( pattern.parse( promoRequest.getStartDate() ) );
        promo.setEndDate( pattern.parse( promoRequest.getEndDate() ) );
        promoService.savePromo( promoRequest.getProductId(), promo );
        return new ResponseEntity<>( HttpStatus.OK );
    }
}
