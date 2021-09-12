package com.project.inventory.store.inventory.repository;

import com.project.inventory.store.inventory.model.GetInventoryTotalStock;
import com.project.inventory.store.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    List<Inventory> findAll();

    @Query( value = "SELECT * " +
            "FROM inventory i " +
            "WHERE i.inventory_id =:inventoryId " +
            "AND i.product_id =:productId",
            nativeQuery = true )
    Optional<Inventory> findByInventoryIdAndProductId( @Param( "inventoryId" ) int inventoryId, @Param( "productId" ) int productId );

    @Modifying
    @Transactional
    @Query( value = "UPDATE inventory " +
            "SET threshold_stock =:threshold " +
            "WHERE inventory_id =:inventoryId",
            nativeQuery = true )
    void updateInventoryThreshold( @Param( "threshold" ) int threshold, @Param( "inventoryId" ) int inventoryId );

    @Modifying
    @Transactional
    @Query( value = "SELECT * " +
            "from inventory " +
            "where product_id =:productId " +
            "AND threshold_stock > 0",
            nativeQuery = true )
    Optional<Inventory> findByProductIdAndThresholdNotZero( @Param( "productId" ) int productId );

    Optional<Inventory> findByProductId( int productId );

//    @Query(value = "select inv.id, sum(s.stock) " +
//            "from inventory inv " +
//            "left join stock s on inv.id = s.inventory_id " +
//            "group by inv.id", nativeQuery = true)
//    List<GetInventoryTotalStock> findAllByStock();
}
