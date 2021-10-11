package com.project.inventory.store.incomingSupply.controller;

import com.project.inventory.store.incomingSupply.model.IncomingSupply;
import com.project.inventory.store.incomingSupply.model.IncomingSupplyDto;
import com.project.inventory.store.incomingSupply.service.IncomingSupplyService;
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
@RequestMapping( value = "api/v1/supplies" )
public class IncomingSupplyController {
    @Autowired
    private IncomingSupplyService incomingSupplyService;

    @RequestMapping( value = "/save", method = RequestMethod.POST )
    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    public ResponseEntity<?> saveIncomingSupplies( @RequestBody IncomingSupply incomingSupply ) {
        incomingSupplyService.saveIncomingSupply( incomingSupply );
        return new ResponseEntity( HttpStatus.OK );
    }

    @RequestMapping( value = "", method = RequestMethod.GET )
    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    public ResponseEntity<?> getIncomingSuppliesByDeliveredStatus( @RequestParam( value = "query", defaultValue = "", required = false ) String query,
                                                                   @RequestParam( value = "status", defaultValue = "", required = false ) String status,
                                                                   @RequestParam( value = "page", defaultValue = "0" ) Integer page,
                                                                   @RequestParam( value = "limit", defaultValue = "0" ) Integer limit ) {
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of( page, limit );
        Page<IncomingSupply> incomingSupplies = incomingSupplyService.getIncomingSupplies( query, status, pageable );
        //convert incoming supply entity to dto
        List<IncomingSupplyDto> incomingSuppliesDto = new ArrayList<>();
        for( IncomingSupply incomingSupply : incomingSupplies.getContent() ) {
            incomingSuppliesDto.add( incomingSupplyService.convertEntityToDto( incomingSupply ) );
        }
        response.put( "data", incomingSuppliesDto );
        response.put( "currentPage", incomingSupplies.getNumber() );
        response.put( "totalItems", incomingSupplies.getTotalElements() );
        response.put( "totalPages", incomingSupplies.getTotalPages() );
        response.put( "totalCounts", incomingSupplyService.getIncomingSupplyCountByStatus() );


        return new ResponseEntity( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    public ResponseEntity<?> getIncomingSupply( @PathVariable int id ) {
        return new ResponseEntity( incomingSupplyService.convertEntityToDto( incomingSupplyService.getIncomingSupply( id ) ), HttpStatus.OK );
    }
    @RequestMapping( value = "/update/{id}", method = RequestMethod.GET )
    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    public ResponseEntity<?> updateIncomingSupply( @PathVariable int id, @RequestBody IncomingSupply incomingSupply ) {
        return new ResponseEntity( HttpStatus.OK );
    }
    @RequestMapping( value = "/delivered", method = RequestMethod.PUT )
    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    public ResponseEntity<?> markIncomingSupplyAsDelivered( @RequestParam( value = "id", defaultValue = "0", required = true ) int id ) {
        incomingSupplyService.markIncomingSuppliesDelivered( id );
        return new ResponseEntity( HttpStatus.OK );
    }
}
