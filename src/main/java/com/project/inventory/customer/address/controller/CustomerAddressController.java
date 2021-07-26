package com.project.inventory.customer.address.controller;

import com.project.inventory.customer.address.model.CustomerAddress;
import com.project.inventory.customer.address.model.CustomerAddressDto;
import com.project.inventory.customer.address.service.CustomerAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/addresses")
public class CustomerAddressController {
    Logger logger = LoggerFactory.getLogger(CustomerAddressController.class);
    @Autowired
    private CustomerAddressService customerAddressService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<CustomerAddressDto> saveCustomerAddress(@RequestBody CustomerAddress customerAddress){
        CustomerAddressDto customerAddressDto = customerAddressService.convertEntityToDto(customerAddressService.saveCustomerAddress(customerAddress));
        return new ResponseEntity(customerAddressDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CustomerAddressDto> getCustomerAddresses(){

        return new ResponseEntity(customerAddressService.getCustomerAddresses(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<CustomerAddressDto> getCustomerAddressesByAccount(@PathVariable int accountId){
        return new ResponseEntity(customerAddressService.getCustomerAddressByAccountId(accountId), HttpStatus.OK);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CustomerAddressDto> updateCustomerAddress(@PathVariable int id, @RequestBody CustomerAddress customerAddress){
        CustomerAddressDto customerAddressDto = customerAddressService.convertEntityToDto(customerAddressService.updateCustomerAddress(id, customerAddress));

        return new ResponseEntity(customerAddressDto, HttpStatus.OK);
    }
}
