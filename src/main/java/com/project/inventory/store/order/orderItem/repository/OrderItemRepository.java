package com.project.inventory.store.order.orderItem.repository;

import com.project.inventory.store.order.orderItem.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
