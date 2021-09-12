package com.project.inventory.store.inventory.controller;

import com.project.inventory.store.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/inventory")
public class InventoryController {
    Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getInventoryTotalStock(){
        return new ResponseEntity(inventoryService.getInventories(), HttpStatus.OK);
    }

//    @RequestMapping(value = "/{inventoryId}/{productId}", method = RequestMethod.GET)
//    public ResponseEntity<?> getInventory(@PathVariable int inventoryId,
//                                  @PathVariable int productId){
//
//        logger.info(String.format("{} %s", inventoryService.getInventory(inventoryId, productId)));
//
//        return new ResponseEntity(inventoryService.getInventory(inventoryId, productId), HttpStatus.OK);
//    }

//    @RequestMapping(value = "/stock/{inventoryId}/{productId}", method =  RequestMethod.PUT)
//    public void addStock(@PathVariable int inventoryId,
//                         @PathVariable int productId,
//                         @RequestParam int stock){
//        inventoryService.addStock(inventoryId, productId, stock);
//    }
//@PreAuthorize( "hasRole('ROLE_CUSTOMER') or hasRole('ROLE_USER')" )
    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public ResponseEntity<?> getInventoryByProductId(@PathVariable int productId){
        logger.info( "{}", productId );
        return new ResponseEntity(inventoryService.getInventoryByProductId( productId ), HttpStatus.OK);
    }
}
