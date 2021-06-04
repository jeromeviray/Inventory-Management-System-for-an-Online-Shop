package com.project.inventory.inventory.controller;

import com.project.inventory.inventory.model.Inventory;
import com.project.inventory.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/inventories")
public class InventoryController {
    Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Inventory> getAllInventory(){
        return inventoryService.getAllInventory();
    }

    @RequestMapping(value = "/{inventory_id}/{product_id}", method = RequestMethod.GET)
    public Inventory getInventory(@PathVariable int inventory_id, @PathVariable int product_id){
        logger.info("{}", inventoryService.getInventory(inventory_id, product_id));
        return inventoryService.getInventory(inventory_id, product_id);
    }

    @RequestMapping(value = "/stock/{inventory_id}/{product_id}", method =  RequestMethod.PUT)
    public void addStock(@PathVariable int product_id,@PathVariable int inventory_id, @RequestParam int stock){
        inventoryService.addStock(inventory_id, product_id, stock);
    }
}
