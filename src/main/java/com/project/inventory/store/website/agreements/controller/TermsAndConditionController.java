package com.project.inventory.store.website.agreements.controller;

import com.project.inventory.store.website.agreements.model.TermsAndCondition;
import com.project.inventory.store.website.agreements.service.TermsAndConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( value = "/api/v1/terms" )
public class TermsAndConditionController {
    @Autowired
    private TermsAndConditionService termsAndConditionService;

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/save", method = RequestMethod.POST )
    public ResponseEntity<?> saveTermsAndCondition( @RequestBody TermsAndCondition termsAndCondition ) {
        termsAndConditionService.saveTermsAndCondition( termsAndCondition );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/update/{id}", method = RequestMethod.PUT )
    public ResponseEntity<?> updateTermsAndCondition( @PathVariable int id, @RequestBody TermsAndCondition termsAndCondition ) {
        return new ResponseEntity<>(termsAndConditionService.updateTermsAndCondition( id, termsAndCondition ), HttpStatus.OK );
    }

    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<?> getTermsAndCondition() {
        return new ResponseEntity<>(termsAndConditionService.getTermsAndCondition(), HttpStatus.OK );
    }
}
