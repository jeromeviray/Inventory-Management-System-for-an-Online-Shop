package com.project.inventory.store.supplier.service.impl;

import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.supplier.model.Supplier;
import com.project.inventory.store.supplier.repository.SupplierRepository;
import com.project.inventory.store.supplier.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    Logger logger = LoggerFactory.getLogger( SupplierServiceImpl.class );

    @Autowired
    private SupplierRepository supplierRepository;


    @Override
    public void saveSupplier( Supplier supplier ) {
        try {
            supplierRepository.save( supplier );
        }catch( Exception e ){
            throw e;
        }
    }

    @Override
    public Supplier updateSupplier( int id, Supplier supplier ) {
        Supplier getSupplier = getSupplier( id );

        getSupplier.setName( supplier.getName() );

        return supplierRepository.save( getSupplier );
    }

    @Override
    public void deleteSupplier( int id ) {
        Supplier supplier = getSupplier( id );

        try{
            supplierRepository.delete( supplier );
        }catch( Exception e ){
            throw  e;
        }
    }

    @Override
    public List<Supplier> getSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier getSupplier( int id ) {
        return supplierRepository.findById( id ).orElseThrow(() -> new NotFoundException("Supplier not Found. "+id) );
    }
}
