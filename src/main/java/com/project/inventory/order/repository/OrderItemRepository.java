package com.project.inventory.order.repository;

import com.project.inventory.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    OrderItem findByCartId(int id);
}
