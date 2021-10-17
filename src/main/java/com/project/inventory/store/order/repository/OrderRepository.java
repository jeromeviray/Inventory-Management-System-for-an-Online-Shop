package com.project.inventory.store.order.repository;

import com.project.inventory.store.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Modifying
    @Query( value = "SELECT * FROM orders WHERE order_status =:status", nativeQuery = true )
    List<Order> findAllByOrderStatus( @Param( "status" ) String status );

    @Modifying
    @Query( value = "SELECT order_status, COUNT(*) as totalCount FROM orders WHERE account_id =:accountId GROUP BY order_status", nativeQuery = true )
    List<Object[]> getCustomerOrderCountGroupBy( @Param( "accountId" ) Integer accountId );

    @Modifying
    @Query( value = "SELECT order_status, COUNT(*) as totalCount FROM orders  GROUP BY order_status", nativeQuery = true )
    List<Object[]> getOrderCountGroupBy();

    @Modifying
    @Query( value = "SELECT * FROM orders WHERE payment_status != 2 AND order_status IN (:status) AND account_id =:id", nativeQuery = true )
    List<Order> findAllByOrderStatusAndAccountId( @Param( "status" ) List<String> status,
                                                  @Param( "id" ) int id );

    @Modifying
    @Query( value = "SELECT * FROM orders WHERE account_id =:id", nativeQuery = true )
    List<Order> findAllByAccountId( @Param( "id" ) int id );

    Optional<Order> findByOrderId( String orderId );

    @Query( value = "SELECT * FROM orders ", nativeQuery = true )
    Page<Order> getPaymentTransactions( Pageable pageable );

    @Query(value = "SELECT product.id, product.product_name, product.product_price,product.barcode, count(product.id) as totalSold FROM inventory_system.order_items item\n" +
            "JOIN inventory_system.products product " +
            "ON item.product_id = product.id " +
            "JOIN inventory_system.orders o " +
            "ON item.order_id = o.id " +
            "WHERE o.order_status = 'PAYMENT_RECEIVED' " +
            "AND (product.product_name LIKE concat('%',:query,'%') OR product.barcode LIKE concat('%',:query,'%')) " +
            "GROUP BY product.id " +
            "ORDER BY totalSold", nativeQuery = true)
    Page<Object[]> getProductsAndTotalSold(@Param("query") String query, Pageable pageable);

}
