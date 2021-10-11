package com.project.inventory.store.incomingSupply.incomingSupplyItem.service;

import com.project.inventory.store.incomingSupply.incomingSupplyItem.model.IncomingSupplyItem;

import java.util.List;

public interface IncomingSupplyItemService {

    void saveIncomingSupplyItem( IncomingSupplyItem incomingSupplyItem );

    void deleteIncomingSupplyItem( int id );

    IncomingSupplyItem updateIncomingSupplyItem( int id, IncomingSupplyItem incomingSupplyItem );

    List<IncomingSupplyItem> getIncomingSupplyItems();

    IncomingSupplyItem getIncomingSupplyItem( int id );

    IncomingSupplyItem checkIfExist( int id );
}
