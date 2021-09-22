package com.project.inventory.store.incomingSupply.model;

import com.project.inventory.store.incomingSupply.incomingSupplyItem.model.IncomingSupplyItemDto;
import com.project.inventory.store.supplier.model.SupplierDto;

import java.util.List;

public class IncomingSupplyDto {
    private int id;
    private String purchasedAt;
    private String deliveredAt;
    private String updatedAt;
    private String incomingSupplyStatus;
    private List<IncomingSupplyItemDto> incomingSupplyItems;
    private SupplierDto supplier;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt( String purchasedAt ) {
        this.purchasedAt = purchasedAt;
    }

    public String getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt( String deliveredAt ) {
        this.deliveredAt = deliveredAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt( String updatedAt ) {
        this.updatedAt = updatedAt;
    }

    public String getIncomingSupplyStatus() {
        return incomingSupplyStatus;
    }

    public void setIncomingSupplyStatus( String incomingSupplyStatus ) {
        this.incomingSupplyStatus = incomingSupplyStatus;
    }

    public List<IncomingSupplyItemDto> getIncomingSupplyItems() {
        return incomingSupplyItems;
    }

    public void setIncomingSupplyItems( List<IncomingSupplyItemDto> incomingSupplyItems ) {
        this.incomingSupplyItems = incomingSupplyItems;
    }

    public SupplierDto getSupplier() {
        return supplier;
    }

    public void setSupplier( SupplierDto supplier ) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
