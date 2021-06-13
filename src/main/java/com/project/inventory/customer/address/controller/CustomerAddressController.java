package com.project.inventory.customer.address.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.project.inventory.customer.address.model.CustomerAddress;
import com.project.inventory.customer.address.service.CustomerAddressService;
import com.project.inventory.jsonView.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/addresses")
public class CustomerAddressController {

    @Autowired
    private CustomerAddressService customerAddressService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<CustomerAddress> saveCustomerAddress(@RequestBody CustomerAddress customerAddress){
        return new ResponseEntity(customerAddressService.saveCustomerAddress(customerAddress), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @JsonView(value = View.CustomerAddress.class)
    public ResponseEntity<CustomerAddress> getCustomerAddresses(){

        return new ResponseEntity(customerAddressService.getCustomerAddresses(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    @JsonView(value = View.CustomerAddress.class)
    public ResponseEntity<CustomerAddress> getCustomerAddressesByAccount(@PathVariable int accountId){

        return new ResponseEntity(customerAddressService.getCustomerAddressByAccountId(accountId), HttpStatus.OK);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    @JsonView(value = View.CustomerAddress.class)
    public ResponseEntity<CustomerAddress> updateCustomerAddress(@PathVariable int id, @RequestBody CustomerAddress customerAddress){
        return new ResponseEntity(customerAddressService.updateCustomerAddress(id, customerAddress), HttpStatus.OK);
    }
}
