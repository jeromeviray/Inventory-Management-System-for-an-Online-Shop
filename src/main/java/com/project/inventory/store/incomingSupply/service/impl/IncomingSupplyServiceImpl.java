package com.project.inventory.store.incomingSupply.service.impl;

import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.incomingSupply.incomingSupplyItem.model.IncomingSupplyItem;
import com.project.inventory.store.incomingSupply.incomingSupplyItem.service.IncomingSupplyItemService;
import com.project.inventory.store.incomingSupply.model.IncomingSupply;
import com.project.inventory.store.incomingSupply.model.IncomingSupplyDto;
import com.project.inventory.store.incomingSupply.model.IncomingSupplyStatus;
import com.project.inventory.store.incomingSupply.repository.IncomingSupplyRepository;
import com.project.inventory.store.incomingSupply.service.IncomingSupplyService;
import com.project.inventory.store.product.service.ProductService;
import com.project.inventory.store.supplier.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        }catch ( Exception e ){
            throw e;
        }
    }

    @Override
    public List<IncomingSupply> getIncomingSupplies() {
        try{
            return incomingSupplyRepository.findAll();
        }catch ( Exception e ){
            throw e;
        }
    }

    @Override
    public List<IncomingSupply> getIncomingSuppliesByPendingStatus() {
        try{
            return incomingSupplyRepository.findAllByIncomingSupplyStatus(IncomingSupplyStatus.PENDING);
        }catch ( Exception e ){
            throw e;
        }
    }

    @Override
    public List<IncomingSupply> getIncomingSuppliesByDeliveredStatus() {
        try{
            return incomingSupplyRepository.findAllByIncomingSupplyStatus(IncomingSupplyStatus.DELIVERED);
        }catch ( Exception e ){
            throw e;
        }
    }

    @Override
    public IncomingSupply getIncomingSupply(int id) {
            return incomingSupplyRepository.findById( id )
                    .orElseThrow(() -> new NotFoundException(String.format( "Incoming Supply not Found.%s", id )) );

    }

    @Override
    public IncomingSupplyDto convertEntityToDto( IncomingSupply incomingSupply ) {
        return mapper.map( incomingSupply, IncomingSupplyDto.class );
    }

    @Override
    public IncomingSupply convertDtoToEntity( IncomingSupplyDto incomingSupplyDto ) {
        return mapper.map( incomingSupplyDto, IncomingSupply.class );
    }

    private void saveIncomingSupplyItems( List<IncomingSupplyItem> incomingSupplyItems, IncomingSupply incomingSupply) {
        for ( IncomingSupplyItem incomingSupplyItem: incomingSupplyItems ){
            incomingSupplyItem.setIncomingSupply( incomingSupply );
            incomingSupplyItem.setProduct( productService.getProductById( incomingSupplyItem.getProduct().getId() ) );
            incomingSupplyItemService.saveIncomingSupplyItem( incomingSupplyItem );
        }
    }

}
