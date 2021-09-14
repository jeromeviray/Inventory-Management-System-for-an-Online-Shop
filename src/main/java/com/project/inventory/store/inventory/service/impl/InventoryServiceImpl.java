package com.project.inventory.store.inventory.service.impl;

import com.project.inventory.exception.invalid.InvalidException;
import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.inventory.model.GetInventoryTotalStock;
import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.model.InventoryDto;
import com.project.inventory.store.inventory.repository.InventoryRepository;
import com.project.inventory.store.inventory.service.InventoryService;
import com.project.inventory.store.inventory.stock.model.Stock;
import com.project.inventory.store.inventory.stock.model.StockStatus;
import com.project.inventory.store.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service( "inventoryServiceImpl" )
public class InventoryServiceImpl implements InventoryService {
    Logger logger = LoggerFactory.getLogger( InventoryServiceImpl.class );

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductService productService;

    @Override
    public void saveInventory( Inventory inventory ) {
        try {
            inventoryRepository.save( inventory );

        } catch ( Exception e ) {
            throw e;
        }
    }

    @Override
    public Inventory getInventoryByProductId( int productId ) {
        return inventoryRepository.findByProductId( productId )
                .orElseThrow( () ->
                        new NotFoundException( String.format( "Inventory not Found with product ID %s", productId ) )
                );

    }

    @Override
    public void updateInventoryThreshold( int id, Inventory inventory ) {
        inventoryRepository.updateInventoryThreshold( inventory.getThreshold(), id );
    }

    @Override
    public Inventory getInventoryByProductIdAndThresholdNotZero( int productId ) {
        return inventoryRepository.findByProductIdAndThresholdNotZero( productId )
                .orElseThrow( () -> new InvalidException( "Threshold is Zero. " +
                        "Please Add Threshold before add new Stock" ) );
    }

    @Override
    public List<GetInventoryTotalStock> getInventories() {
        List<GetInventoryTotalStock> inventoryTotalStocks = new ArrayList<>();
        try {
            for ( Inventory inventory : inventoryRepository.findAll() ) {
                int sum = 0;

                for ( Stock stock : inventory.getStock() ) {
                    if ( inventory.getId() == stock.getInventory().getId() ) {
                        sum += stock.getStock();
                    }
                }

                GetInventoryTotalStock getInventoryTotalStock = new GetInventoryTotalStock();
                getInventoryTotalStock.setId( inventory.getId() );
                getInventoryTotalStock.setBarcode( inventory.getProduct().getBarcode() );
                getInventoryTotalStock.setProductName(
                        productService.getProductById(
                                        inventory.getProduct()
                                                .getId() )
                                .getName() );
                getInventoryTotalStock.setThreshold( inventory.getThreshold() );
                getInventoryTotalStock.setTotalStock( sum );
                getInventoryTotalStock.setStatus(
                        checkThresholdAndStock( getInventoryTotalStock, inventory.getProduct().getId() )
                                .getStatus().toString()
                );
                inventoryTotalStocks.add( getInventoryTotalStock );
            }
            return inventoryTotalStocks;
        } catch ( Exception e ) {
            throw e;
        }
    }

    @Override
    public Inventory getInventory( int productId ) {
        return null;
    }

    @Override
    public InventoryDto convertEntityToDto( Inventory inventory ) {
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setStatus( inventory.getStatus().toString() );
        inventoryDto.setThreshold( inventory.getThreshold() );
        int sum = 0;
        for ( Stock stock : inventory.getStock() ) {
            sum += stock.getStock();
        }
        inventoryDto.setTotalStock( sum );
        return inventoryDto;
    }

    private Inventory checkThresholdAndStock( GetInventoryTotalStock getInventoryTotalStock, int productId ) {
        Inventory inventory = getInventoryByProductId( productId );
        if ( getInventoryTotalStock.getTotalStock() > getInventoryTotalStock.getThreshold() ) {
            inventory.setStatus( StockStatus.OK );
        } else if ( getInventoryTotalStock.getTotalStock() < getInventoryTotalStock.getThreshold() ) {
            inventory.setStatus( StockStatus.LOW );
        } else {
            inventory.setStatus( StockStatus.OUT_OF_STOCK );
        }
        return inventoryRepository.save( inventory );
    }

}
