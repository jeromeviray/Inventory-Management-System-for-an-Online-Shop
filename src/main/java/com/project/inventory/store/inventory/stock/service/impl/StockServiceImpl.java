package com.project.inventory.store.inventory.stock.service.impl;

import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.service.InventoryService;
import com.project.inventory.store.inventory.stock.model.Stock;
import com.project.inventory.store.inventory.stock.repository.StockRepository;
import com.project.inventory.store.inventory.stock.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StockServiceImpl implements StockService {
    Logger logger = LoggerFactory.getLogger( StockServiceImpl.class );
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private StockRepository stockRepository;

    @Override
    public void addStock( Stock stock, int productId ) {
        try{
            Inventory inventory = inventoryService.getInventoryByProductId( productId );
            stock.setInventory( inventory );
            stock.setStockId( UUID.randomUUID().toString() );
            stockRepository.save( stock );
        }catch ( Exception e ){
            throw e;
        }

    }

    @Override
    public void updateStock( Stock stock, int stockId ) {
        Stock savedStock = getStockById( stockId );
        savedStock.setStock( stock.getStock() );
        stockRepository.save( savedStock );
    }

    @Override
    public Stock getStockById( int stockId ) {
        return stockRepository.findById(stockId).orElseThrow(() -> new NotFoundException("Stock not found") );
    }

    @Override
    public Inventory getStockByInventoryId( int inventoryId ) {
        return null;
    }

    @Override
    public List<Stock> getStocks(int inventoryId) {
        return stockRepository.findAllByInventoryId( inventoryId );
    }

    @Override
    public Integer getProductStocks( Integer productId ) {
        return stockRepository.getProductStocks( productId );
    }
}
