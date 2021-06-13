package com.project.inventory.order.shoppingOrder.controller;

import com.project.inventory.cart.cartItem.model.CartItem;
import com.project.inventory.order.shoppingOrder.model.ShoppingOrder;
import com.project.inventory.order.shoppingOrder.service.ShoppingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/orders")
public class ShoppingOrderController {

    @Autowired
    private ShoppingOrderService shoppingOrderService;

    @RequestMapping(value = "/place", method = RequestMethod.POST)
    public ResponseEntity<ShoppingOrder> placeOrder(@RequestParam int customerAddressId,
                                                    @RequestParam int paymentId,
                                                    @RequestBody List<CartItem> cartItems){

        return new ResponseEntity(shoppingOrderService
                .placeOrder(customerAddressId,
                paymentId,
                cartItems),
                HttpStatus.OK);
    }
}
