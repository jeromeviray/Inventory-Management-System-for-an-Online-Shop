package com.project.inventory.store.incomingSupply.repository;

import com.project.inventory.store.incomingSupply.model.IncomingSupply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface IncomingSupplyRepository extends JpaRepository<IncomingSupply, Integer> {

    @Query(value = "SELECT * FROM incoming_supplies supply WHERE status =:status OR supply_reference LIKE concat('%',:query,'%')",
            countQuery = "SELECT count(*) FROM incoming_supplies WHERE status =:status",
            nativeQuery = true)
    Page<IncomingSupply> findAllByIncomingSupplyStatus( @Param( "query" ) String query, @Param( "status" ) String status, Pageable pageable );
}
