package com.project.inventory.order.orderItems.service;

import com.project.inventory.order.orderItems.model.OrderItem;
import com.project.inventory.order.orderItems.model.OrderItemDto;

import java.util.List;

public interface OrderItemService {
   void saveOrderItem(List<OrderItem> orderItems);
   List<OrderItemDto> getAllOrderItems();

   OrderItemDto convertEntityToDto(OrderItem orderItem);

   OrderItem convertDtoToEntity(OrderItemDto orderItemDto);

   List<OrderItemDto> convertOrderItemsToDto(List<OrderItem> orderItems);

   OrderItemDto getOrderItem(int id);
}
