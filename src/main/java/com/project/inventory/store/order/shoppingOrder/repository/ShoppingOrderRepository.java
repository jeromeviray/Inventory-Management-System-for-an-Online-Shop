package com.project.inventory.store.order.shoppingOrder.repository;

import com.project.inventory.store.order.shoppingOrder.model.ShoppingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShoppingOrderRepository extends JpaRepository<ShoppingOrder, Integer> {

    @Query(value = "SELECT * FROM shopping_order WHERE order_status = 0", nativeQuery = true)
    List<ShoppingOrder> findAllByStatusPending();

}
