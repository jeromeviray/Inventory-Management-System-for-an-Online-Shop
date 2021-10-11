package com.project.inventory.store.order.repository;

import com.project.inventory.store.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

//    @Query( value = "SELECT * FROM order_management WHERE order_status = 'PENDING'", nativeQuery = true )
//    List<Order> findAllByStatusPending();
//
//    @Query( value = "SELECT * FROM order_management WHERE order_status = 'CONFIRMED'", nativeQuery = true )
//    List<Order> findAllByStatusConfirmed();
//
//    @Query( value = "SELECT * FROM order_management WHERE order_status = 'COMPLETED'", nativeQuery = true )
//    List<Order> findAllByStatusCompleted();

    @Modifying
    @Query( value = "SELECT * FROM orders WHERE order_status =:status", nativeQuery = true )
    List<Order> findAllByOrderStatus( @Param( "status" ) String status );

    @Modifying
    @Query( value = "SELECT order_status, COUNT(*) as totalCount FROM orders WHERE account_id =:accountId GROUP BY order_status", nativeQuery = true )
    List<Object[]> getCustomerOrderCountGroupBy( @Param( "accountId" ) Integer accountId );

    @Modifying
    @Query( value = "SELECT order_status, COUNT(*) as totalCount FROM orders  GROUP BY order_status", nativeQuery = true )
    List<Object[]> getOrderCountGroupBy( );

    @Modifying
    @Query( value = "SELECT * FROM orders WHERE order_status IN (:status) AND account_id =:id", nativeQuery = true )
    List<Order> findAllByOrderStatusAndAccountId( @Param( "status" ) List<String> status,
                                                  @Param( "id" ) int id );

    @Modifying
    @Query( value = "SELECT * FROM orders WHERE account_id =:id", nativeQuery = true )
    List<Order> findAllByAccountId( @Param( "id" ) int id );

    Optional<Order> findByOrderId( String orderId);
}
