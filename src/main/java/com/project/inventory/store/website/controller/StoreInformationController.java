package com.project.inventory.store.website.controller;

import com.project.inventory.store.website.service.StoreInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping( value = "/api/v1/store" )
public class StoreInformationController {
    @Autowired
    private StoreInformationService storeInformationService;

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/save", method = RequestMethod.POST )
    public ResponseEntity<?> saveStoreInformation( @RequestPart( value = "logoImage", required = false ) MultipartFile logoImage,
                                                   @RequestParam( "storeName" ) String storeName,
                                                   @RequestParam( "domainName" ) String domainName,
                                                   @RequestParam( "location" ) String location,
                                                   @RequestParam( "description" ) Object description,
                                                   @RequestParam( "contact" ) String contactNumber,
                                                   @RequestParam( "email" ) String email ) throws IOException {

        storeInformationService.saveStoreInformation( logoImage, storeName, domainName, location, description, contactNumber, email );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/update/{id}", method = RequestMethod.POST )
    public ResponseEntity<?> updateStoreInformation( @PathVariable int id,
                                                     @RequestPart( value = "logoImage", required = false ) MultipartFile logoImage,
                                                     @RequestParam( "storeName" ) String storeName,
                                                     @RequestParam( "domainName" ) String domainName,
                                                     @RequestParam( "location" ) String location,
                                                     @RequestParam( "description" ) Object description,
                                                     @RequestParam( "removeLogo" ) String removeLogo,
                                                     @RequestParam( "contact" ) String contactNumber,
                                                     @RequestParam( "email" ) String email ) throws IOException {

        return new ResponseEntity<>(
                storeInformationService.updateStoreInformation(
                        id,
                        logoImage,
                        storeName,
                        domainName,
                        location,
                        description,
                        removeLogo,
                        contactNumber,
                        email ), HttpStatus.OK );
    }

    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<?> getStoreInformation() throws IOException {

        return new ResponseEntity<>( storeInformationService.getStoreInformation(), HttpStatus.OK );
    }
}
