package com.project.inventory.order.shoppingOrder.service;

import com.project.inventory.cart.cartItem.model.CartItem;
import com.project.inventory.order.shoppingOrder.model.ShoppingOrder;
import com.project.inventory.order.shoppingOrder.model.ShoppingOrderDto;

import java.util.List;

public interface ShoppingOrderService {
    void placeOrder(int customerAddressId,
                             int paymentId,
                             List<CartItem> cartItems);
    List<ShoppingOrderDto> getOrders();
    ShoppingOrderDto convertEntityToDto(ShoppingOrder shoppingOrder);
    ShoppingOrder convertDtoToEntity(ShoppingOrderDto shoppingOrderDto);
}
