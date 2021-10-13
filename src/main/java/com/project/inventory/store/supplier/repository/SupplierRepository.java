package com.project.inventory.store.supplier.repository;

import com.project.inventory.store.supplier.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    @Query( value = "SELECT * FROM suppliers where is_deleted = 0 AND name LIKE concat('%',:query, '%')",
            countQuery = "SELECT count(*) FROM suppliers WHERE is_deleted = 0",
            nativeQuery = true )
    Page<Supplier> findAll( @Param( "query" ) String query, Pageable pageable );

    @Query(value = "SELECT * FROM suppliers WHERE id=:id", nativeQuery = true)
    Supplier findBySupplierId(@Param( "id" ) int id);
}
