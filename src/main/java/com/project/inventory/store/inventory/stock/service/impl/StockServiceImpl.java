package com.project.inventory.store.inventory.stock.service.impl;

import com.project.inventory.exception.invalid.InvalidException;
import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.service.InventoryService;
import com.project.inventory.store.inventory.stock.model.Stock;
import com.project.inventory.store.inventory.stock.repository.StockRepository;
import com.project.inventory.store.inventory.stock.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            //check if the threshold is less than or equal zero
            // if true it will throw invalid exception.
            // before adding new stock
            // required to add threshold first
            if(inventory.getThreshold() <= 0) throw new InvalidException("Threshold is Zero. " +
                    "Please Add Threshold before add new Stock");

            stock.setInventory( inventory );
            stock.setStockId( UUID.randomUUID().toString() );
            stockRepository.save( stock );
        }catch ( Exception e ){
            throw e;
        }

    }

    @Override
    public void updateStock( Stock stock, int productId ) {

    }

    @Override
    public Stock getStockById( int stockId ) {
        return null;
    }

    @Override
    public Inventory getStockByInventoryId( int inventoryId ) {
        return null;
    }
}
