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
        promoService.savePromo( promoRequest );
        return new ResponseEntity<>( HttpStatus.OK );

    }

    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<?> getPromos() throws ParseException {
        return new ResponseEntity<>( promoService.getPromos(), HttpStatus.OK );
    }
}
