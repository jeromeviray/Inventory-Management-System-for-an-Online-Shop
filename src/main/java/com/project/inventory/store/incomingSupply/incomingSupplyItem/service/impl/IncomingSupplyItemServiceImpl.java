package com.project.inventory.store.incomingSupply.incomingSupplyItem.service.impl;

import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.incomingSupply.incomingSupplyItem.model.IncomingSupplyItem;
import com.project.inventory.store.incomingSupply.incomingSupplyItem.repository.IncomingSupplyItemRepository;
import com.project.inventory.store.incomingSupply.incomingSupplyItem.service.IncomingSupplyItemService;
import jdk.jfr.Frequency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomingSupplyItemServiceImpl implements IncomingSupplyItemService {
    Logger logger = LoggerFactory.getLogger( IncomingSupplyItemServiceImpl.class );

    @Autowired
    private IncomingSupplyItemRepository incomingSupplyItemRepository;

    @Override
    public void saveIncomingSupplyItem( IncomingSupplyItem incomingSupplyItem ) {
        try{
            incomingSupplyItemRepository.save( incomingSupplyItem );
        }catch ( Exception e ){
            throw e;
        }
    }

    @Override
    public void deleteIncomingSupplyItem( int id ) {
        try {
            logger.info( String.format( "Delete Incoming Item. %s", id ) );
            incomingSupplyItemRepository.delete( getIncomingSupplyItem( id ) );
        }catch ( Exception e ){
            throw e;
        }
    }

    @Override
    public IncomingSupplyItem updateIncomingSupplyItem( int id, IncomingSupplyItem incomingSupplyItem ) {
        try {
            logger.info( String.format( "Update Incoming Item. %s", id ) );

            IncomingSupplyItem savedIncomingSupplyItem = getIncomingSupplyItem( id );
            savedIncomingSupplyItem.setNumberReceived( incomingSupplyItem.getNumberReceived() );
            savedIncomingSupplyItem.setProduct( incomingSupplyItem.getProduct() );

            return incomingSupplyItemRepository.save( savedIncomingSupplyItem );
        }catch ( Exception e ){
            throw e;
        }
    }

    @Override
    public List<IncomingSupplyItem> getIncomingSupplyItems() {
        try{
            return incomingSupplyItemRepository.findAll();
        }catch ( Exception e ){
            throw e;
        }
    }

    @Override
    public IncomingSupplyItem getIncomingSupplyItem( int id ) {
        return incomingSupplyItemRepository.findById( id )
                .orElseThrow(() -> new NotFoundException("Incoming Supply Item not Found. "+id) );
    }
}