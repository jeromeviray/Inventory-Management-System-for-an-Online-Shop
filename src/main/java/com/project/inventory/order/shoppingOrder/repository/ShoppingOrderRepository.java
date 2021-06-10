package com.project.inventory.order.shoppingOrder.repository;

import com.project.inventory.order.shoppingOrder.model.ShoppingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingOrderRepository extends JpaRepository<ShoppingOrder, Integer> {
}
