package com.project.inventory.store.order.orderManagement.controller;

import com.project.inventory.store.order.orderManagement.model.PlaceOrder;
import com.project.inventory.store.order.orderManagement.model.OrderDto;
import com.project.inventory.store.order.orderManagement.service.OrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/orders")
public class ShoppingOrderController {

    @Autowired
    private OrderManagementService orderManagementService;

    @RequestMapping(value = "/place", method = RequestMethod.POST)
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrder placeOrder){
        orderManagementService.placeOrder(
                placeOrder.getCustomerAddressId(),
                placeOrder.getPaymentId(),
                placeOrder.getCartItems());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<OrderDto> getOrders(){
        return new ResponseEntity(orderManagementService.getOrders(), HttpStatus.OK);
    }

}
