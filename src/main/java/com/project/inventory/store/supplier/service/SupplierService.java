package com.project.inventory.store.supplier.service;

import com.project.inventory.store.supplier.model.Supplier;
import com.project.inventory.store.supplier.model.SupplierDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplierService {

    void saveSupplier( Supplier supplier );

    Supplier updateSupplier( int id, Supplier supplier );

    void deleteSupplier(int id);

    Page<Supplier> getSuppliers( String query, Pageable pageable );

    Supplier getSupplier( int id );

    SupplierDto convertEntityToDto(Supplier supplier);

    Supplier getSupplierById(int id);

}
