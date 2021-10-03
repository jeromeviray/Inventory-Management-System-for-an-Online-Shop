package com.project.inventory.store.inventory.repository;

import com.project.inventory.store.inventory.model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    @Query( value = "SELECT * " +
            "FROM inventories i " +
            "JOIN products as p ON p.id = i.product_id " +
            "WHERE (p.product_name LIKE concat('%',:query,'%') " +
            "OR p.barcode LIKE concat('%',:query,'%')) AND p.product_is_deleted = 0 ",
            nativeQuery = true )
    Page<Inventory> findAll( @Param( "query" ) String query, Pageable pageable );

    @Modifying
    @Transactional
    @Query( value = "UPDATE inventory " +
            "SET threshold_stock =:threshold " +
            "WHERE inventory_id =:inventoryId",
            nativeQuery = true )
    void updateInventoryThreshold( @Param( "threshold" ) int threshold, @Param( "inventoryId" ) int inventoryId );


    @Query( value = "SELECT * FROM inventories where product_id =:productId", nativeQuery = true )
    Optional<Inventory> findByProductId( @Param( "productId" ) int productId );

//    @Query(value = "select inv.id, sum(s.stock) " +
//            "from inventory inv " +
//            "left join stock s on inv.id = s.inventory_id " +
//            "group by inv.id", nativeQuery = true)
//    List<GetInventoryTotalStock> findAllByStock();
}
