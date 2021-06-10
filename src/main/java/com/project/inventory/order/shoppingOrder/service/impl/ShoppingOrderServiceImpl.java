package com.project.inventory.order.shoppingOrder.service.impl;

import com.project.inventory.cart.cartItem.model.CartItem;
import com.project.inventory.order.shoppingOrder.model.ShoppingOrder;
import com.project.inventory.order.shoppingOrder.service.ShoppingOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingOrderServiceImpl implements ShoppingOrderService {
    @Override
    public ShoppingOrder placeOrder(List<CartItem> cartItems) {
        return null;
    }
}
