package com.project.inventory.store.inventory.repository;

import com.project.inventory.store.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    List<Inventory> findAll();
    @Query(value = "SELECT * FROM inventory i WHERE i.inventory_id =:inventory_id AND i.product_id =:product_id",
            nativeQuery = true)
    Inventory findByInventoryIdAndProductId(@Param("inventory_id") int inventory_id, @Param("product_id") int product_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE inventory SET stock =:stock WHERE inventory_id =:inventory_id", nativeQuery = true)
    void updateInventory(@Param("stock") int stock, @Param("inventory_id") int inventory_id);
}
