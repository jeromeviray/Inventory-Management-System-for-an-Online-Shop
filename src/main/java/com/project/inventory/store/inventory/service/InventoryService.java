package com.project.inventory.store.inventory.service;

import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.model.InventoryDto;
import com.project.inventory.store.product.model.Product;

public interface InventoryService {

    void saveInventory( Inventory inventory );

    Inventory getInventoryByProductId( int productId );

    void updateInventoryThreshold( int id, Inventory inventory );

    InventoryDto convertEntityToDto( Inventory inventory );

    Inventory checkThresholdAndStock( InventoryDto inventoryDto, int productId );

    int getTotalStocks( Product product );
}
