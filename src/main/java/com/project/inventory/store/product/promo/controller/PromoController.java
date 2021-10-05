package com.project.inventory.store.product.promo.controller;

import com.project.inventory.store.product.promo.model.Promo;
import com.project.inventory.store.product.promo.model.PromoDto;
import com.project.inventory.store.product.promo.model.PromoRequest;
import com.project.inventory.store.product.promo.model.PromoStatus;
import com.project.inventory.store.product.promo.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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


    @RequestMapping( value = "/save", method = RequestMethod.POST )
    public ResponseEntity<?> savePromo( @RequestBody PromoRequest promoRequest ) throws Exception {
        SimpleDateFormat pattern = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss" );
        Date current = new Date();
        Promo promo = new Promo();
        int compared = pattern.parse( pattern.format( current ) ).compareTo( pattern.parse( promoRequest.getStartDate() ) );

        if( compared > 0 ) {
            promo.setStatus( PromoStatus.ONGOING );
        } else if( compared < 0 ) {
            promo.setStatus( PromoStatus.UNSCHEDULED );
        } else {
            promo.setStatus( PromoStatus.ONGOING );
        }
        promo.setPercentage( promoRequest.getPercentage() );
        promo.setQuantity( promoRequest.getQuantity() );
        promo.setStartDate( pattern.parse( promoRequest.getStartDate() ) );
        promo.setEndDate( pattern.parse( promoRequest.getEndDate() ) );
        promoService.savePromo( promoRequest.getProductId(), promo );
        return new ResponseEntity<>( HttpStatus.OK );

    }

    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<?> getPromos() throws ParseException {
        List<PromoDto> promos = new ArrayList<>();
        SimpleDateFormat pattern = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss" );

        for( Promo promo : promoService.getPromos() ) {
            Date current = new Date();
            int startDate = pattern.format( pattern.parse( pattern.format(promo.getStartDate()) ) ).compareTo(pattern.format( pattern.parse( pattern.format( current ) ) ));
            int endDate = pattern.format( pattern.parse( pattern.format(promo.getEndDate()) ) ).compareTo(pattern.format( pattern.parse( pattern.format( current ) ) ));
            if(startDate > 0){
                if(endDate < 0){
                    promo.setStatus( PromoStatus.END );
                }else {
                    promo.setStatus( PromoStatus.ONGOING );
                }
            }
                if(endDate < 0){
                    promo.setStatus( PromoStatus.END );
                }else {
                    promo.setStatus( PromoStatus.ONGOING );
                }
            promoService.updatePromo( promo.getId(), promo );
            promos.add( promoService.convertEntityToDto( promo ) );
        }
        return new ResponseEntity<>( promos, HttpStatus.OK );
    }
}
