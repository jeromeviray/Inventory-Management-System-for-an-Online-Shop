package com.project.inventory.store.inventory.service.impl;

import com.project.inventory.exception.invalid.InvalidException;
import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.inventory.model.GetProductDto;
import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.model.InventoryDto;
import com.project.inventory.store.inventory.repository.InventoryRepository;
import com.project.inventory.store.inventory.service.InventoryService;
import com.project.inventory.store.inventory.stock.model.Stock;
import com.project.inventory.store.inventory.stock.model.StockStatus;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private ModelMapper mapper;

    @Override
    public void saveInventory( Inventory inventory ) {
        try {
            inventoryRepository.save( inventory );

        } catch( Exception e ) {
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
    public Page<InventoryDto> getInventories(Pageable pageable) {
        try {
            Page<Inventory> inventories = inventoryRepository.findAll(pageable);
            List<InventoryDto> pageRecords = new ArrayList<>();
            for( Inventory inventory : inventories.getContent() ) {
                int sum = 0;

                for( Stock stock : inventory.getStock() ) {
                    if( inventory.getId() == stock.getInventory().getId() ) {
                        sum += stock.getStock();
                    }
                }
                InventoryDto inventoryDto = new InventoryDto();
                inventoryDto.setProduct( convertProductEntityToDto( inventory.getProduct() ) );
                inventoryDto.setThreshold( inventory.getThreshold() );
                inventoryDto.setTotalStock( sum );
                inventoryDto.setStatus(
                        checkThresholdAndStock( inventoryDto, inventory.getProduct().getId() )
                                .getStatus().toString()
                );
                pageRecords.add( inventoryDto );
            }
            return new PageImpl<>(pageRecords, pageable, inventories.getTotalElements());
        } catch( Exception e ) {
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
        for( Stock stock : inventory.getStock() ) {
            sum += stock.getStock();
        }
        inventoryDto.setTotalStock( sum );
        return inventoryDto;
    }

    private Inventory checkThresholdAndStock( InventoryDto inventoryDto, int productId ) {
        Inventory inventory = getInventoryByProductId( productId );
        if( inventoryDto.getTotalStock() > inventoryDto.getThreshold() ) {
            inventory.setStatus( StockStatus.OK );
        } else if( inventoryDto.getTotalStock() < inventoryDto.getThreshold() ) {
            inventory.setStatus( StockStatus.LOW );
        } else {
            inventory.setStatus( StockStatus.OUT_OF_STOCK );
        }
        return inventoryRepository.save( inventory );
    }

    private GetProductDto convertProductEntityToDto( Product product ) {
        return mapper.map( product, GetProductDto.class );
    }
}
