package com.project.inventory.store.order.orderManagement.controller;

import com.project.inventory.store.order.orderManagement.model.PlaceOrder;
import com.project.inventory.store.order.orderManagement.model.OrderDto;
import com.project.inventory.store.order.orderManagement.service.OrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
        @RequestMapping( value = "api/v1/orders" )
public class OrderManagementController {

    @Autowired
    private OrderManagementService orderManagementService;

    @RequestMapping( value = "/place", method = RequestMethod.POST )
    public ResponseEntity<?> placeOrder( @RequestBody PlaceOrder placeOrder ) {
        orderManagementService.placeOrder(
                placeOrder.getCustomerAddressId(),
                placeOrder.getPaymentId(),
                placeOrder.getCartItems() );
        return new ResponseEntity( HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_CUSTOMER') or hasRole('ROLE_USER')" )
    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<OrderDto> getOrdersByAccountId() {
        return new ResponseEntity( orderManagementService.getOrdersByAccountId(), HttpStatus.OK );
    }

    @RequestMapping( value = "/pending", method = RequestMethod.GET )
    public ResponseEntity<OrderDto> getPendingOrders() {
        return new ResponseEntity( orderManagementService.getPendingOrders(), HttpStatus.OK );
    }

    @RequestMapping( value = "/confirmed", method = RequestMethod.GET )
    public ResponseEntity<OrderDto> getConfirmedOrders() {
        return new ResponseEntity( orderManagementService.getConfirmedOrders(), HttpStatus.OK );
    }

    @RequestMapping( value = "/completed", method = RequestMethod.GET )
    public ResponseEntity<OrderDto> getCompletedOrders() {
        return new ResponseEntity( orderManagementService.getCompletedOrders(), HttpStatus.OK );
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<?> getOrder( @PathVariable String orderId ){
        return new ResponseEntity(orderManagementService.convertEntityToDto(
                orderManagementService.getOrderByOrderId( orderId )
        ), HttpStatus.OK);
    }
}
