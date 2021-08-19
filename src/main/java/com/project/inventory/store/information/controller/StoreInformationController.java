package com.project.inventory.store.information.controller;

import com.project.inventory.store.information.model.StoreInformation;
import com.project.inventory.store.information.service.StoreInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( value = "api/v1/store" )
public class StoreInformationController {
    @Autowired
    private StoreInformationService storeInformationService;

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = "/save", method = RequestMethod.POST )
    public ResponseEntity<?> saveStoreInformation( @RequestBody StoreInformation storeInformation ) {
        return new ResponseEntity( storeInformationService.saveStoreInformation( storeInformation ), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<?> getStores() {
        return new ResponseEntity( storeInformationService.getStores(), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = "/products/{branch}", method = RequestMethod.GET )
    public ResponseEntity<?> getProductByBranch( @PathVariable String branch ) {
        return new ResponseEntity( storeInformationService.getProductsByBranch( branch ), HttpStatus.OK );
    }
}
