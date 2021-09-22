package com.project.inventory.store.incomingSupply.controller;

import com.project.inventory.store.incomingSupply.model.IncomingSupply;
import com.project.inventory.store.incomingSupply.model.IncomingSupplyDto;
import com.project.inventory.store.incomingSupply.service.IncomingSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/supplies")
public class IncomingSupplyController {
    @Autowired
    private IncomingSupplyService incomingSupplyService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    public ResponseEntity<?> saveIncomingSupplies( @RequestBody IncomingSupply incomingSupply ){
        incomingSupplyService.saveIncomingSupply( incomingSupply );
        return new ResponseEntity( HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    public ResponseEntity<?> getIncomingSupplies(  ){
        List<IncomingSupplyDto> incomingSupplies = new ArrayList<>();
        for(IncomingSupply incomingSupply : incomingSupplyService.getIncomingSupplies()){
            incomingSupplies.add( incomingSupplyService.convertEntityToDto( incomingSupply ) );
        }
        return new ResponseEntity(incomingSupplies, HttpStatus.OK);
    }

    @RequestMapping(value = "/pending", method = RequestMethod.GET)
    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    public ResponseEntity<?> getIncomingSuppliesByPendingStatus(  ){
        List<IncomingSupplyDto> incomingSupplies = new ArrayList<>();
        for(IncomingSupply incomingSupply : incomingSupplyService.getIncomingSuppliesByPendingStatus()){
            incomingSupplies.add( incomingSupplyService.convertEntityToDto( incomingSupply ) );
        }
        return new ResponseEntity(incomingSupplies, HttpStatus.OK);
    }
    @RequestMapping(value = "/delivered", method = RequestMethod.GET)
    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    public ResponseEntity<?> getIncomingSuppliesByDeliveredStatus(  ){
        List<IncomingSupplyDto> incomingSupplies = new ArrayList<>();
        for(IncomingSupply incomingSupply : incomingSupplyService.getIncomingSuppliesByDeliveredStatus()){
            incomingSupplies.add( incomingSupplyService.convertEntityToDto( incomingSupply ) );
        }
        return new ResponseEntity(incomingSupplies, HttpStatus.OK);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    public ResponseEntity<?> getIncomingSupply( @PathVariable int id ){
        return new ResponseEntity(incomingSupplyService.convertEntityToDto( incomingSupplyService.getIncomingSupply(id) ) , HttpStatus.OK);
    }
}
