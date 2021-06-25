package com.project.inventory.store.order.orderItems.repository;

import com.project.inventory.store.order.orderItems.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
