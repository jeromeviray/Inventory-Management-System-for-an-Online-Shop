package com.project.inventory.store.website.controller;

import com.project.inventory.store.website.model.StoreInformation;
import com.project.inventory.store.website.service.StoreInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping( value = "/api/v1/store" )
public class StoreInformationController {
    @Autowired
    private StoreInformationService storeInformationService;

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/save", method = RequestMethod.POST )
    public ResponseEntity<?> saveStoreInformation( @RequestParam( "storeName" ) String storeName,
                                                   @RequestParam( "acronym" ) String acronym,
                                                   @RequestParam( "location" ) String location,
                                                   @RequestParam( "description" ) Object description,
                                                   @RequestParam( "contact" ) String contactNumber,
                                                   @RequestParam( "email" ) String email ) throws IOException {

        storeInformationService.saveStoreInformation( storeName, acronym, location, description, contactNumber, email );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/update/{id}", method = RequestMethod.PUT )
    public ResponseEntity<?> updateStoreInformation( @PathVariable int id,
                                                     @RequestBody StoreInformation storeInformation ) throws IOException {

        return new ResponseEntity<>(
                storeInformationService.updateStoreInformation(
                        id,
                        storeInformation.getStoreName(),
                        storeInformation.getAcronym(),
                        storeInformation.getLocation(),
                        storeInformation.getDescription(),
                        storeInformation.getContactNumber(),
                        storeInformation.getEmail() ), HttpStatus.OK );
    }

    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<?> getStoreInformation() throws IOException {

        return new ResponseEntity<>( storeInformationService.getStoreInformation(), HttpStatus.OK );
    }
}
