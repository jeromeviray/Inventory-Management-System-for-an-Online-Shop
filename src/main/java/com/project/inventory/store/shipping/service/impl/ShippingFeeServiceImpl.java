package com.project.inventory.store.shipping.service.impl;

import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.shipping.model.ShippingFee;
import com.project.inventory.store.shipping.repository.ShippingFeeRepository;
import com.project.inventory.store.shipping.service.ShippingFeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingFeeServiceImpl implements ShippingFeeService {
    Logger logger = LoggerFactory.getLogger( ShippingFeeServiceImpl.class );

    @Autowired
    private ShippingFeeRepository shippingFeeRepository;

    @Override
    public List<ShippingFee> getShippingFees() {
        List<ShippingFee> shippingFees = shippingFeeRepository.findAll();
        if(shippingFees.isEmpty()){
            ShippingFee shippingFee = new ShippingFee();
            shippingFee.setShippingAmount( 200 );
            ShippingFee savedShippingFee = shippingFeeRepository.save( shippingFee );
            shippingFees.add( savedShippingFee );
        }
        return shippingFees;
    }

    @Override
    public ShippingFee getShippingFeeById( int id ) {
        return shippingFeeRepository.findById( id ).orElseThrow(()-> new NotFoundException("Shipping Fee NOt Found") );
    }

    @Override
    public void saveShippingFee( ShippingFee shippingFee ) {

    }

    @Override
    public void updateShippingFee( int id, ShippingFee shippingFee ) {

    }
}
