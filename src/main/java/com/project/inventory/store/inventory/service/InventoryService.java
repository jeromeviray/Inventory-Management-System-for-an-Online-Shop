package com.project.inventory.store.inventory.service;

import com.project.inventory.store.inventory.model.GetInventoryTotalStock;
import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.model.InventoryDto;

import java.util.List;

public interface InventoryService {

    void saveInventory( Inventory inventory );

    List<GetInventoryTotalStock> getInventories();

    Inventory getInventory( int productId );

    Inventory getInventoryByProductId( int productId );

    void updateInventoryThreshold( int id, Inventory inventory );

    Inventory getInventoryByProductIdAndThresholdNotZero( int productId );

//    void addStock( int inventory_id, int product_id, int stock );

    InventoryDto convertEntityToDto( Inventory inventory );

}
