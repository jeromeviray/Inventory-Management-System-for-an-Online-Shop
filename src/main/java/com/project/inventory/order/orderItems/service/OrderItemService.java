package com.project.inventory.order.orderItems.service;

import com.project.inventory.order.orderItems.model.OrderItem;

public interface OrderItemService {
    void saveItem(int productId, OrderItem orderItem);
    void addItem();
}
