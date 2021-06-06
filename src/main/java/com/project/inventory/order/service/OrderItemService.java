package com.project.inventory.order.service;

import com.project.inventory.order.model.OrderItem;

public interface OrderItemService {
    void saveItem(int productId, OrderItem orderItem);
    void addItem();
}
