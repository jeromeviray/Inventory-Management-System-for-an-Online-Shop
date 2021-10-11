package com.project.inventory.store.incomingSupply.model;

import com.project.inventory.store.incomingSupply.incomingSupplyItem.model.IncomingSupplyItem;
import com.project.inventory.store.supplier.model.Supplier;

import java.util.List;

public class RequestUpdateIncomingSupply {
    private IncomingSupply incomingSupply;
    private List<IncomingSupplyItem> removedItems;

    public IncomingSupply getIncomingSupply() {
        return incomingSupply;
    }

    public void setIncomingSupply( IncomingSupply incomingSupply ) {
        this.incomingSupply = incomingSupply;
    }

    public List<IncomingSupplyItem> getRemovedItems() {
        return removedItems;
    }

    public void setRemovedItems( List<IncomingSupplyItem> removedItems ) {
        this.removedItems = removedItems;
    }

    @Override
    public String toString() {
            return super.toString();
    }
}
