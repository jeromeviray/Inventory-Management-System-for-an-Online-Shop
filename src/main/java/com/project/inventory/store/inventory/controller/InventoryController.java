package com.project.inventory.store.inventory.controller;

import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.service.InventoryService;
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

    @RequestMapping(value = "/{inventoryId}/{productId}", method = RequestMethod.GET)
    public Inventory getInventory(@PathVariable int inventoryId,
                                  @PathVariable int productId){

        logger.info(String.format("{} %s", inventoryService.getInventory(inventoryId, productId)));

        return inventoryService.getInventory(inventoryId, productId);
    }

    @RequestMapping(value = "/stock/{inventoryId}/{productId}", method =  RequestMethod.PUT)
    public void addStock(@PathVariable int inventoryId,
                         @PathVariable int productId,
                         @RequestParam int stock){
        inventoryService.addStock(inventoryId, productId, stock);
    }
}
