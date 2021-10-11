package com.project.inventory.store.incomingSupply.incomingSupplyItem.repository;

import com.project.inventory.store.incomingSupply.incomingSupplyItem.model.IncomingSupplyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IncomingSupplyItemRepository extends JpaRepository<IncomingSupplyItem, Integer> {

    @Query( value = "SELECT * FROM incoming_supply_items WHERE id =:id", nativeQuery = true )
    IncomingSupplyItem checkIfExist( @Param( "id" ) int id );

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM incoming_supply_items WHERE id =:id", nativeQuery = true)
    void deleteIncomingSupplyItem(@Param( "id" ) int id);
}
