package com.project.inventory.store.incomingSupply.repository;

import com.project.inventory.store.incomingSupply.model.IncomingSupply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IncomingSupplyRepository extends JpaRepository<IncomingSupply, Integer> {

    @Query(value = "SELECT * FROM incoming_supplies supply WHERE status =:status AND supply_reference LIKE concat('%',:query,'%')",
            countQuery = "SELECT count(*) FROM incoming_supplies",
            nativeQuery = true)
    Page<IncomingSupply> findAllByIncomingSupplyStatus( @Param( "query" ) String query, @Param( "status" ) String status, Pageable pageable );

    @Query(value = "SELECT status, COUNT(*) as totalCount FROM incoming_supplies  GROUP BY status", nativeQuery = true)
    List<Object[]> findAllAndCountByStatus();
}
