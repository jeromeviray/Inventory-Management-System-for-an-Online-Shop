package com.project.inventory.order.orderItems.repository;

import com.project.inventory.order.orderItems.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
