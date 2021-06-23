package com.project.inventory.order.orderItems.service.impl;

import com.project.inventory.cart.service.CartService;
import com.project.inventory.exception.order.OrderItemNotFoundException;
import com.project.inventory.order.orderItems.model.OrderItem;
import com.project.inventory.order.orderItems.model.OrderItemDto;
import com.project.inventory.order.orderItems.repository.OrderItemRepository;
import com.project.inventory.order.orderItems.service.OrderItemService;
import com.project.inventory.product.service.ProductService;
import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper mapper;

    @Override
    public void saveOrderItem(List<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);
    }

    @Override
    public List<OrderItemDto> getAllOrderItems() {
        List<OrderItemDto> orderItems = new ArrayList<>();

        for (OrderItem orderItem : orderItemRepository.findAll()){
            orderItems.add(convertEntityToDto(orderItem));
        }
        return orderItems;
    }

    @Override
    public OrderItemDto convertEntityToDto(OrderItem orderItem) {
        return mapper.map(orderItem, OrderItemDto.class);
    }

    @Override
    public OrderItem convertDtoToEntity(OrderItemDto orderItemDto) {
        return mapper.map(orderItemDto, OrderItem.class);
    }

    @Override
    public List<OrderItemDto> convertOrderItemsToDto(List<OrderItem> orderItems) {
        List<OrderItemDto> convertedOrderItemsToDto = new ArrayList<>();
        for (OrderItem orderItem : orderItems){
            convertedOrderItemsToDto.add(convertEntityToDto(orderItem));
        }
        return convertedOrderItemsToDto;
    }

    @Override
    public OrderItemDto getOrderItem(int id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(String.format("Order Item Not Found with ID: "+ id)));
        return convertEntityToDto(orderItem);
    }
}
