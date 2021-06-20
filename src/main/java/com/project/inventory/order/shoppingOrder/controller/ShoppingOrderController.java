package com.project.inventory.order.shoppingOrder.controller;

import com.project.inventory.order.shoppingOrder.model.PlaceOrder;
import com.project.inventory.order.shoppingOrder.model.ShoppingOrder;
import com.project.inventory.order.shoppingOrder.model.ShoppingOrderDto;
import com.project.inventory.order.shoppingOrder.service.ShoppingOrderService;
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
    private ShoppingOrderService shoppingOrderService;

    @RequestMapping(value = "/place", method = RequestMethod.POST)
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrder placeOrder){
        shoppingOrderService.placeOrder( placeOrder.getCustomerAddressId(),
                placeOrder.getPaymentId(),
                placeOrder.getCartItems());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ShoppingOrderDto> getOrders(){
        return new ResponseEntity(shoppingOrderService.getOrders(), HttpStatus.OK);
    }

}
