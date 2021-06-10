package com.project.inventory.order.orderItems.service.impl;

import com.project.inventory.cart.service.CartService;
import com.project.inventory.order.orderItems.model.OrderItem;
import com.project.inventory.order.orderItems.repository.OrderItemRepository;
import com.project.inventory.order.orderItems.service.OrderItemService;
import com.project.inventory.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    Logger logger = LoggerFactory.getLogger(OrderItemServiceImpl.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void saveOrderItem(OrderItem orderItem) {

    }
}
