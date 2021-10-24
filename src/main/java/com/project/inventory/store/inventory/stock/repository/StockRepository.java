package com.project.inventory.store.inventory.stock.repository;

import com.project.inventory.store.inventory.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Integer> {
//
    @Query(value = "SELECT * FROM stocks stock WHERE stock.inventory_id =:inventoryId AND stock.stock > 0", nativeQuery = true)
    List<Stock> findAllByInventoryId(@Param( "inventoryId" ) int inventoryId);

    @Query(value = "SELECT SUM(stock.stock) FROM stocks stock " +
            "JOIN inventories as inv ON inv.id = stock.inventory_id " +
            "WHERE inv.product_id = :productId AND stock.stock > 0",
            nativeQuery = true)
    Integer getProductStocks(@Param( "productId" ) int productId);
}
