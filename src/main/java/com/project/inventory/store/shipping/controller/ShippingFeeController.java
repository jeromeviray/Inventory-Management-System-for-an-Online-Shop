package com.project.inventory.store.shipping.controller;

import com.project.inventory.store.shipping.service.ShippingFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/shipping")
public class ShippingFeeController {
    @Autowired
    private ShippingFeeService shippingFeeService;

    @RequestMapping(value = "/fees", method = RequestMethod.GET)
    public ResponseEntity<?> getShippingFees(){
        return new ResponseEntity<>( shippingFeeService.getShippingFees(), HttpStatus.OK );
    }

}
