package com.project.inventory.store.inventory.stock.service;

import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.stock.model.Stock;

import java.util.List;

public interface StockService {
    void addStock( Stock stock, int productId );

    void updateStock( Stock stock, int stockId );

    Stock getStockById( int stockId );

    Inventory getStockByInventoryId( int inventoryId );

    List<Stock> getStocks(int inventoryId);
}
