package com.project.inventory.store.inventory.controller;

import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.model.InventoryDto;
import com.project.inventory.store.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/inventory")
public class InventoryController {
    Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getInventoryTotalStock(
            @RequestParam( value = "query", defaultValue = "") String query,
            @RequestParam( value = "page", defaultValue = "0" ) Integer page,
            @RequestParam( value = "limit", defaultValue = "10" ) Integer limit
        ){

        Map<String, Object> response = new HashMap<>();

        Pageable c = PageRequest.of(page, limit);
        Page<InventoryDto> inventories = inventoryService.getInventories(query, c);
        response.put("data", inventories.getContent());
        response.put("currentPage", inventories.getNumber());
        response.put("totalItems", inventories.getTotalElements());
        response.put("totalPages", inventories.getTotalPages());


        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public ResponseEntity<?> getInventoryByProductId(@PathVariable int productId){
        return new ResponseEntity(inventoryService.convertEntityToDto( inventoryService.getInventoryByProductId( productId ) ), HttpStatus.OK);
    }
}
