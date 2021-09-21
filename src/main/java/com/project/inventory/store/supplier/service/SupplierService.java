package com.project.inventory.store.supplier.service;

import com.project.inventory.store.supplier.model.Supplier;

import java.util.List;

public interface SupplierService {

    void saveSupplier( Supplier supplier );

    Supplier updateSupplier( int id, Supplier supplier );

    void deleteSupplier(int id);

    List<Supplier> getSuppliers();

    Supplier getSupplier( int id );

}
