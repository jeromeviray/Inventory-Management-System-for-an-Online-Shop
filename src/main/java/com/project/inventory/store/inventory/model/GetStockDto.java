package com.project.inventory.store.inventory.model;

import com.project.inventory.store.inventory.stock.model.Stock;

import java.util.List;

public class GetStockDto {
    private List<Stock> stock;

    public List<Stock> getStock() {
        return stock;
    }

    public void setStock( List<Stock> stock ) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
