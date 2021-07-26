package com.project.inventory.store.order.orderManagement.service;

import com.project.inventory.store.cart.cartItem.model.CartItem;
import com.project.inventory.store.order.orderManagement.model.Order;
import com.project.inventory.store.order.orderManagement.model.OrderDto;

import java.util.List;

public interface OrderManagementService {
    void placeOrder(int customerAddressId,
                             int paymentId,
                             List<CartItem> cartItems);
    List<OrderDto> getOrders();
    OrderDto convertEntityToDto(Order order);
    Order convertDtoToEntity(OrderDto orderDto);
}
