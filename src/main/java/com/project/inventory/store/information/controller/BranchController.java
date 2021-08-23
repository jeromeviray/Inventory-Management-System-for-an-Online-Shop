package com.project.inventory.store.information.controller;

import com.project.inventory.store.information.model.Branch;
import com.project.inventory.store.information.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( value = "api/v1/branches" )
public class BranchController {
    @Autowired
    private BranchService branchService;

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = "/save", method = RequestMethod.POST )
    public ResponseEntity<?> saveStoreInformation( @RequestBody Branch branch ) {
        return new ResponseEntity( branchService.saveStoreInformation( branch ), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/options", method = RequestMethod.GET )
    public ResponseEntity<?> getStores() {
        return new ResponseEntity( branchService.getStores(), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = "/products/{branch}", method = RequestMethod.GET )
    public ResponseEntity<?> getProductByBranch( @PathVariable String branch ) {
        return new ResponseEntity( branchService.getProductsByBranch( branch ), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<?> getBranchWithTotalProduct() {
        return new ResponseEntity(branchService.getBranchWithTotalProduct(), HttpStatus.OK);
    }
}
