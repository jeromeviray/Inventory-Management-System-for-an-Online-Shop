package com.project.inventory.store.inventory.service.impl;

import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.inventory.model.GetProductDto;
import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.model.InventoryDto;
import com.project.inventory.store.inventory.repository.InventoryRepository;
import com.project.inventory.store.inventory.service.InventoryService;
import com.project.inventory.store.inventory.stock.model.Stock;
import com.project.inventory.store.inventory.stock.model.StockStatus;
import com.project.inventory.store.inventory.stock.service.StockService;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service( "inventoryServiceImpl" )
public class InventoryServiceImpl implements InventoryService {
    Logger logger = LoggerFactory.getLogger( InventoryServiceImpl.class );

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductService productService;
    @Autowired
    private StockService stockService;

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
        Inventory inventory = inventoryRepository.findByProductId( productId )
                .orElseThrow( () ->
                        new NotFoundException( String.format( "Inventory not Found with product ID %s", productId ) )
                );
        return inventory;

    }

    @Override
    public void updateInventoryThreshold( int id, Inventory inventory ) {
        inventoryRepository.updateInventoryThreshold( inventory.getThreshold(), id );
    }

    @Override
    public InventoryDto convertEntityToDto( Inventory inventory ) {
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setTotalStock( getTotalStocks( inventory.getProduct() ) );
        inventoryDto.setThreshold( inventory.getThreshold() );
        inventoryDto.setStatus( checkThresholdAndStock( inventoryDto, inventory.getProduct().getId() ).getStatus().name() );
        return inventoryDto;
    }

    @Override
    public int getTotalStocks( Product product ) {
        int sum = 0;
        Inventory inventory = getInventoryByProductId( product.getId() );

        for( Stock stock : inventory.getStock() ) {
            if( inventory.getId() == stock.getInventory().getId() ) {
                sum += stock.getStock();
            }
        }
        return sum;
    }

    @Override
    public Inventory checkThresholdAndStock( InventoryDto inventoryDto, int productId ) {
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

    @Override
    public void updateThreshold( int productId, int threshold ) {
        Inventory inventory = getInventoryByProductId( productId );
        inventory.setThreshold( threshold );

        inventoryRepository.save( inventory );
    }

    @Override
    public void updateStock( int productId, int quantity ) {
        Inventory inventory = getInventoryByProductId( productId );
        for( Stock stock : stockService.getStocks( inventory.getId() ) ) {
            if( stock.getStock() > 0 ) {
                int minusQuantity = quantity;
                if( quantity > stock.getStock() ) {
                    minusQuantity = quantity - stock.getStock();
                } else if( minusQuantity == 0 ) {
                    break;
                }
                stock.setStock( stock.getStock() - minusQuantity );
                stockService.updateStock( stock, stock.getId() );

                if( stock.getStock() > quantity ) {
                    break;
                }
            }
        }
    }

    @Override
    public void cancelStock( int productId, int quantity ) {
        Inventory inventory = getInventoryByProductId( productId );
        for( Stock stock : stockService.getStocks( inventory.getId() ) ) {
            stock.setStock( stock.getStock() + quantity );

            stockService.updateStock( stock, stock.getId() );
            break;
        }
    }

    private GetProductDto convertProductEntityToDto( Product product ) {
        return mapper.map( product, GetProductDto.class );
    }
}
