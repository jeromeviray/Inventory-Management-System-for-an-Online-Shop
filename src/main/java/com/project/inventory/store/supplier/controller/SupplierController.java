package com.project.inventory.store.supplier.controller;

import com.project.inventory.store.supplier.model.Supplier;
import com.project.inventory.store.supplier.model.SupplierDto;
import com.project.inventory.store.supplier.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( value = "api/v1/suppliers" )
public class SupplierController {

    @Autowired
    private SupplierService supplierService;


    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/create", method = RequestMethod.POST )
    public ResponseEntity<?> saveSupplier( @RequestBody Supplier supplier ) {
        supplierService.saveSupplier( supplier );
        return new ResponseEntity( HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/update/{id}", method = RequestMethod.PUT )
    public ResponseEntity<?> updateSupplier( @PathVariable int id, @RequestBody Supplier supplier ) {
        return new ResponseEntity( supplierService.updateSupplier( id, supplier ), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/delete/{id}", method = RequestMethod.DELETE )
    public ResponseEntity<?> deleteSupplier( @PathVariable int id ) {
        supplierService.deleteSupplier( id );
        return new ResponseEntity( HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<?> getSuppliers( @RequestParam( value = "query", defaultValue = "", required = false ) String query,
                                           @RequestParam( value = "page", defaultValue = "0" ) Integer page,
                                           @RequestParam( value = "limit", defaultValue = "0" ) Integer limit) {

        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of( page, limit );
        Page<Supplier> suppliers = supplierService.getSuppliers( query, pageable );

        response.put( "data", suppliers.getContent() );
        response.put( "currentPage", suppliers.getNumber() );
        response.put( "totalItems", suppliers.getTotalElements() );
        response.put( "totalPages", suppliers.getTotalPages() );

        return new ResponseEntity(response, HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public ResponseEntity<?> getSupplier( @PathVariable int id ) {
        return new ResponseEntity( supplierService.getSupplier( id ), HttpStatus.OK );
    }
}
