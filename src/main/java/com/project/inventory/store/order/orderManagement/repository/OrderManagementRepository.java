package com.project.inventory.store.order.orderManagement.repository;

import com.project.inventory.store.order.orderManagement.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderManagementRepository extends JpaRepository<Order, Integer> {

    @Query(value = "SELECT * FROM shopping_order WHERE order_status = 0", nativeQuery = true)
    List<Order> findAllByStatusPending();

}
