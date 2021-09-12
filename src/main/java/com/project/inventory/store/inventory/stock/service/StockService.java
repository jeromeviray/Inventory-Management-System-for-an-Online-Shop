package com.project.inventory.store.inventory.stock.service;

import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.stock.model.Stock;

public interface StockService {
    void addStock( Stock stock, int productId );

    void updateStock( Stock stock, int productId );

    Stock getStockById( int stockId );

    Inventory getStockByInventoryId( int inventoryId );
}
