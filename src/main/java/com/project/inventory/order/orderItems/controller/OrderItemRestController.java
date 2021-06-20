package com.project.inventory.order.orderItems.controller;

import com.project.inventory.order.orderItems.model.OrderItem;
import com.project.inventory.order.orderItems.service.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/order/items")
public class OrderItemRestController {
    Logger logger = LoggerFactory.getLogger(OrderItemRestController.class);
    @Autowired
    private OrderItemService orderItemService;

//    @RequestMapping(value = "/add/{productId}", method = RequestMethod.POST)
//    public void addItem(@PathVariable int productId, @RequestBody OrderItem orderItem){
//        orderItemService.saveItem(productId, orderItem);
//    }
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAllOrderItems(){
        return new ResponseEntity(orderItemService.getAllOrderItems(), HttpStatus.OK);
    }
}
