package com.project.inventory.store.product.promo.repository;

import com.project.inventory.store.product.promo.model.Promo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PromoRepository extends JpaRepository<Promo, Integer> {

    @Query(value = "SELECT * FROM product_promos WHERE status =:status", nativeQuery = true)
    List<Promo> findAllByStatus( @Param ( "status" ) String status );

    @Query(value = "SELECT * FROM product_promos WHERE product_id =:productId " +
            " and (status = 'ONGOING' OR status = 'UNSCHEDULED')", nativeQuery = true)
    Promo findByProductIdAndStatusOngoingOrUnscheduled( @Param( "productId" ) int id);
}
