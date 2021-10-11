package com.project.inventory.store.incomingSupply.service.impl;

import com.project.inventory.exception.invalid.InvalidException;
import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.incomingSupply.incomingSupplyItem.model.IncomingSupplyItem;
import com.project.inventory.store.incomingSupply.incomingSupplyItem.service.IncomingSupplyItemService;
import com.project.inventory.store.incomingSupply.model.IncomingSupply;
import com.project.inventory.store.incomingSupply.model.IncomingSupplyDto;
import com.project.inventory.store.incomingSupply.model.IncomingSupplyStatus;
import com.project.inventory.store.incomingSupply.repository.IncomingSupplyRepository;
import com.project.inventory.store.incomingSupply.service.IncomingSupplyService;
import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.service.InventoryService;
import com.project.inventory.store.inventory.stock.model.Stock;
import com.project.inventory.store.inventory.stock.service.StockService;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.service.ProductService;
import com.project.inventory.store.supplier.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IncomingSupplyServiceImpl implements IncomingSupplyService {
    Logger logger = LoggerFactory.getLogger( IncomingSupplyServiceImpl.class );

    @Autowired
    private IncomingSupplyRepository incomingSupplyRepository;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private IncomingSupplyItemService incomingSupplyItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private StockService stockService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public void saveIncomingSupply( IncomingSupply incomingSupplyRequest ) {
        try {
            IncomingSupply incomingSupply = new IncomingSupply();
            incomingSupply.setIncomingSupplyStatus( IncomingSupplyStatus.PENDING );
            incomingSupply.setSupplier( supplierService.getSupplier( incomingSupplyRequest.getSupplier().getId() ) );
            //saved incoming supply and pass to the incoming supply item
            IncomingSupply savedIncomingSupply = incomingSupplyRepository.save( incomingSupply );
            saveIncomingSupplyItems( incomingSupplyRequest.getIncomingSupplyItems(), savedIncomingSupply );
        } catch ( Exception e ) {
            throw e;
        }
    }

    @Override
    public Map<String, BigInteger> getIncomingSupplyCountByStatus() {
        List<Object[]> counts = incomingSupplyRepository.findAllAndCountByStatus();

        Map<String, BigInteger> totals = new HashMap<>();
        for ( Object[] result : counts ) {
            totals.put( result[ 0 ].toString(), ( BigInteger ) result[ 1 ] );
        }
        return totals;
    }

    @Override
    public Page<IncomingSupply> getIncomingSupplies( String query, String status, Pageable pageable ) {
        try {
            return incomingSupplyRepository.findAllByIncomingSupplyStatus( query, status, pageable );
        } catch ( Exception e ) {
            throw e;
        }
    }

    @Override
    public IncomingSupply getIncomingSupply( int id ) {
        return incomingSupplyRepository.findById( id )
                .orElseThrow( () -> new NotFoundException( String.format( "Incoming Supply not Found.%s", id ) ) );

    }

    @Override
    public IncomingSupplyDto convertEntityToDto( IncomingSupply incomingSupply ) {
        return mapper.map( incomingSupply, IncomingSupplyDto.class );
    }

    @Override
    public IncomingSupply convertDtoToEntity( IncomingSupplyDto incomingSupplyDto ) {
        return mapper.map( incomingSupplyDto, IncomingSupply.class );
    }

    private void saveIncomingSupplyItems( List<IncomingSupplyItem> incomingSupplyItems, IncomingSupply incomingSupply ) {
        for ( IncomingSupplyItem incomingSupplyItem : incomingSupplyItems ) {
            incomingSupplyItem.setIncomingSupply( incomingSupply );
            incomingSupplyItem.setProduct( productService.getProductById( incomingSupplyItem.getProduct().getId() ) );
            incomingSupplyItemService.saveIncomingSupplyItem( incomingSupplyItem );
        }
    }

    @Override
    public void markIncomingSuppliesDelivered( int id ) {
        IncomingSupply incomingSupply = getIncomingSupply( id );
        if ( incomingSupply.getIncomingSupplyStatus().equals( IncomingSupplyStatus.PENDING ) ) {
            for ( IncomingSupplyItem item : incomingSupply.getIncomingSupplyItems() ) {
                Stock stock = new Stock();
                stock.setStock( item.getNumberReceived() );
                stockService.addStock( stock, item.getProduct().getId() );
            }
            incomingSupply.setIncomingSupplyStatus( IncomingSupplyStatus.DELIVERED );
            incomingSupplyRepository.save( incomingSupply );
        } else {
            throw new InvalidException( "This Supplies already added in Inventory" );
        }

    }

    @Override
    public void updateIncomingSupply( int id, IncomingSupply incomingSupply, List<IncomingSupplyItem> incomingSupplyItems ) {

        IncomingSupply savedIncomingSupply = getIncomingSupply( id );
        savedIncomingSupply.setSupplier( supplierService.getSupplier( incomingSupply.getSupplier().getId() ) );
        savedIncomingSupply.setIncomingSupplyStatus( IncomingSupplyStatus.PENDING );

        if ( !incomingSupplyItems.isEmpty() ) {
            logger.info( "Remove items" );
            for ( IncomingSupplyItem incomingSupplyItem : incomingSupplyItems ) {
                if ( incomingSupplyItemService.checkIfExist( incomingSupplyItem.getId() ) != null ) {
                    incomingSupplyItemService.deleteIncomingSupplyItem( incomingSupplyItem.getId() );
                }
            }
        }
        for ( IncomingSupplyItem incomingSupplyItem : incomingSupply.getIncomingSupplyItems() ) {
            logger.info( "Update Incoming Item product {}", incomingSupplyItem.getProduct().getId() );

            if ( incomingSupplyItemService.checkIfExist( incomingSupplyItem.getId() ) != null ) {
                incomingSupplyItemService.updateIncomingSupplyItem( incomingSupplyItem.getId(), incomingSupplyItem );
            } else {
                incomingSupplyItem.setIncomingSupply( savedIncomingSupply );
                incomingSupplyItem.setProduct( productService.getProductById( incomingSupplyItem.getProduct().getId() ) );
                incomingSupplyItemService.saveIncomingSupplyItem( incomingSupplyItem );
            }
        }
    }
}
