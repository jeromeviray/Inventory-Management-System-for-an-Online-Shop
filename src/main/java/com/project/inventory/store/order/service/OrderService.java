package com.project.inventory.store.order.service;

import com.project.inventory.store.cart.cartItem.model.CartItemDto;
import com.project.inventory.store.order.model.Order;
import com.project.inventory.store.order.model.OrderDto;
import com.project.inventory.store.shipping.model.ShippingFee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Order placeOrder( int customerAddressId,
                      int paymentMethodId,
                      List<CartItemDto> cartItems,
                      ShippingFee shippingFee);

    Page<OrderDto> getOrdersByStatus(String status, String query, Pageable pageable);

    Page<Order> getPaymentTransactions( String query, Pageable pageable);

    Map<String, BigInteger> getOrderCountByStatus();

    List<OrderDto> getOrdersByAccountId();

    Order getOrderByOrderId(String orderId);

    OrderDto convertEntityToDto( Order order );

    Order convertDtoToEntity( OrderDto orderDto );

    void saveOrder(Order order);

}
