package com.project.inventory.store.incomingSupply.service;

import com.project.inventory.store.incomingSupply.model.IncomingSupply;
import com.project.inventory.store.incomingSupply.model.IncomingSupplyDto;

import java.util.List;

public interface IncomingSupplyService {

    void saveIncomingSupply( IncomingSupply incomingSupply );

    List<IncomingSupply> getIncomingSupplies();

    List<IncomingSupply> getIncomingSuppliesByPendingStatus();

    List<IncomingSupply> getIncomingSuppliesByDeliveredStatus();

    IncomingSupply getIncomingSupply(int id);

    IncomingSupplyDto convertEntityToDto(IncomingSupply incomingSupply);

    IncomingSupply convertDtoToEntity(IncomingSupplyDto incomingSupplyDto);
}
