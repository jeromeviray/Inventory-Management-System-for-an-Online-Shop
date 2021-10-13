package com.project.inventory.store.supplier.service.impl;

import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.supplier.model.Supplier;
import com.project.inventory.store.supplier.model.SupplierDto;
import com.project.inventory.store.supplier.repository.SupplierRepository;
import com.project.inventory.store.supplier.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements SupplierService {
    Logger logger = LoggerFactory.getLogger( SupplierServiceImpl.class );

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ModelMapper mapper;

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
            supplier.setDeleted( true );
            saveSupplier( supplier );
        }catch( Exception e ){
            throw  e;
        }
    }

    @Override
    public Page<Supplier> getSuppliers( String query, Pageable pageable ) {
        try {
            return supplierRepository.findAll(query, pageable);
        }catch( Exception e ){
            throw e;
        }
    }

    @Override
    public Supplier getSupplier( int id ) {
        return supplierRepository.findById( id ).orElseThrow(() -> new NotFoundException("Supplier not Found. "+id) );
    }

    @Override
    public Supplier getSupplierById( int id ) {
        return supplierRepository.findBySupplierId( id );
    }

    @Override
    public SupplierDto convertEntityToDto( Supplier supplier ) {
        return mapper.map( supplier, SupplierDto.class );
    }
}
