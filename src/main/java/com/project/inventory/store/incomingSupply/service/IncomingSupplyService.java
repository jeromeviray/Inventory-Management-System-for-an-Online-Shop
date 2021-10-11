package com.project.inventory.store.incomingSupply.service;

import com.project.inventory.store.incomingSupply.model.IncomingSupply;
import com.project.inventory.store.incomingSupply.model.IncomingSupplyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface IncomingSupplyService {

    void saveIncomingSupply( IncomingSupply incomingSupply );

    Map<String, BigInteger> getIncomingSupplyCountByStatus();

    Page<IncomingSupply> getIncomingSupplies(String query, String status, Pageable pageable );

    IncomingSupply getIncomingSupply( int id );

    IncomingSupplyDto convertEntityToDto( IncomingSupply incomingSupply );

    IncomingSupply convertDtoToEntity( IncomingSupplyDto incomingSupplyDto );

    void markIncomingSuppliesDelivered(int id);

    void updateIncomingSupply(int id, IncomingSupply incomingSupply);
}
