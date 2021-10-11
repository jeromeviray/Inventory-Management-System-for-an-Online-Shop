package com.project.inventory.store.order.service;

import com.project.inventory.store.cart.cartItem.model.CartItem;
import com.project.inventory.store.order.model.Order;
import com.project.inventory.store.order.model.OrderDto;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Order placeOrder( int customerAddressId,
                     int paymentMethodId,
                     List<CartItem> cartItems );

    List<OrderDto> getOrdersByStatus(String status);

    Map<String, BigInteger> getOrderCountByStatus();

    List<OrderDto> getOrdersByAccountId();

    Order getOrderByOrderId(String orderId);

    OrderDto convertEntityToDto( Order order );

    Order convertDtoToEntity( OrderDto orderDto );

    void saveOrder(Order order);
}
