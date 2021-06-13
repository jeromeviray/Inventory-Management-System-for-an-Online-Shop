package com.project.inventory.customer.payment.controller;

import com.project.inventory.customer.payment.model.PaymentMethod;
import com.project.inventory.customer.payment.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/payment/methods")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<PaymentMethod> getPaymentMethods(){
        return new ResponseEntity(paymentMethodService.getPaymentMethods(), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<PaymentMethod> getPaymentMethodById(@PathVariable int id){
        return new ResponseEntity(paymentMethodService.getPaymentMethodById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "{paymentMethodName}", method = RequestMethod.GET)
    public ResponseEntity<PaymentMethod> getPaymentMethodByPaymentMethod(@PathVariable String paymentMethodName){
        return new ResponseEntity(paymentMethodService.getPaymentMethodByName(paymentMethodName), HttpStatus.OK);
    }
}
