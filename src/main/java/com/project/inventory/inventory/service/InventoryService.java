package com.project.inventory.inventory.service;

import com.project.inventory.inventory.model.Inventory;

import java.util.List;

public interface InventoryService {

    List<Inventory> getAllInventory();
    Inventory getInventory(int inventory_id, int product_id);
    void addStock(int inventory_id, int product_id, int stock);

}
