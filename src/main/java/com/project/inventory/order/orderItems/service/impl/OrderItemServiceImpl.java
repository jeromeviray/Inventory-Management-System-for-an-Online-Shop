package com.project.inventory.order.service.impl;

import com.project.inventory.cart.model.Cart;
import com.project.inventory.cart.repository.CartRepository;
import com.project.inventory.cart.service.CartService;
import com.project.inventory.exception.product.ProductNotFound;
import com.project.inventory.order.model.OrderItem;
import com.project.inventory.order.repository.OrderItemRepository;
import com.project.inventory.order.service.OrderItemService;
import com.project.inventory.product.model.Product;
import com.project.inventory.product.repository.ProductRepository;
import com.project.inventory.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public void saveItem(int productId, OrderItem orderItem) {

    }

    @Override
    public void addItem() {

    }
}
