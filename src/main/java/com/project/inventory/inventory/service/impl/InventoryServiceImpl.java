package com.project.inventory.inventory.service.impl;

import com.project.inventory.exception.product.ProductNotFound;
import com.project.inventory.inventory.model.Inventory;
import com.project.inventory.inventory.repository.InventoryRepository;
import com.project.inventory.inventory.service.InventoryService;
import com.project.inventory.product.model.Product;
import com.project.inventory.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


@Service("inventoryServiceImpl")
public class InventoryServiceImpl implements InventoryService {
    Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();

    }

    @Override
    public Inventory getInventory(int inventory_id, int product_id) {
        Inventory inventory = inventoryRepository.findByInventoryIdAndProductId(inventory_id, product_id);
        logger.info("{}", inventory);
        if(inventory == null){
            throw new ProductNotFound("Product not found");
        }
        return inventory;
    }

    @Override
    public void addStock(int inventory_id, int product_id, int stock) {
        Inventory inventory = inventoryRepository.findByInventoryIdAndProductId(inventory_id, product_id);
        if(inventory == null) throw new ProductNotFound("Product not found");

        int stocks = inventory.getStock() + stock;
        logger.info("{}", stocks);
        inventoryRepository.updateInventory(stocks, inventory.getId());
//        return inventory;
    }
}
