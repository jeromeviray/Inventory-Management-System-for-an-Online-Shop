package com.project.inventory.order.orderItems.service;

import com.project.inventory.order.orderItems.model.OrderItem;

import java.util.List;

public interface OrderItemService {
   void saveOrderItem(List<OrderItem> orderItems);
}
