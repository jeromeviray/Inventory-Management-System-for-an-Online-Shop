package com.project.inventory.store.incomingSupply.service;

import com.project.inventory.store.incomingSupply.model.IncomingSupply;
import com.project.inventory.store.incomingSupply.model.IncomingSupplyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IncomingSupplyService {

    void saveIncomingSupply( IncomingSupply incomingSupply );

    List<IncomingSupply> getIncomingSupplies();

    Page<IncomingSupply> getIncomingSuppliesByPendingStatus( String query, Pageable pageable );

    Page<IncomingSupply> getIncomingSuppliesByDeliveredStatus( String query, Pageable pageable );

    IncomingSupply getIncomingSupply( int id );

    IncomingSupplyDto convertEntityToDto( IncomingSupply incomingSupply );

    IncomingSupply convertDtoToEntity( IncomingSupplyDto incomingSupplyDto );
}
