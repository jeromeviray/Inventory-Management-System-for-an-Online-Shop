package com.project.inventory.store.order.orderManagement.controller;

import com.project.inventory.api.payment.PaymongoAPI;
import com.project.inventory.common.user.model.UserDto;
import com.project.inventory.store.inventory.service.impl.InventoryServiceImpl;
import com.project.inventory.store.order.orderManagement.model.*;
import com.project.inventory.store.order.orderManagement.service.OrderManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
        @RequestMapping( value = "api/v1/orders" )
public class OrderManagementController {
    Logger logger = LoggerFactory.getLogger( OrderManagementController.class );

    @Autowired
    private OrderManagementService orderManagementService;

    private PaymongoAPI paymongoAPI = new PaymongoAPI();

    @RequestMapping( value = "/checkout", method = RequestMethod.POST )
    public ResponseEntity<?> placeOrder( @RequestBody PlaceOrder placeOrder ) {
        OrderResponse response = new OrderResponse();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            Order order = orderManagementService.placeOrder(
                    placeOrder.getCustomerAddressId(),
                    placeOrder.getPaymentId(),
                    placeOrder.getCartItems() );
            logger.info(order.getPaymentMethod().getPaymentMethod());
            if( Objects.equals( order.getPaymentMethod().getPaymentMethod(), "GCASH" ) ) {
                Map resp = this.paymongoAPI.generateSource( order.getTotalAmount(), "PHP",  "http://localhost:4000/cart/123/payment/success", "http://localhost:4000/cart/123/payment/failed" );
                Map data = (Map ) resp.get("data");
                Map attributes = (Map) data.get("attributes");
                Map redirect = (Map) attributes.get("redirect");
                response.setRedirectUrl( ( String ) redirect.get("checkout_url") );
            }
        } catch( Exception e ) {
            httpStatus = HttpStatus.BAD_REQUEST;
            response.setSuccess( false );
            response.setMessage( e.getMessage() );
        }
        return new ResponseEntity( response, httpStatus );
    }

    @PreAuthorize( "hasRole('ROLE_CUSTOMER') or hasRole('ROLE_USER')" )
    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<OrderDto> getOrdersByAccountId() {
        return new ResponseEntity( orderManagementService.getOrdersByAccountId(), HttpStatus.OK );
    }

    @RequestMapping( value = "/status/{status}", method = RequestMethod.GET )
    public ResponseEntity<Map> getOrdersByStatus(@PathVariable(value = "status") String status ) {
        Map response = new HashMap();
        response.put("orders", orderManagementService.getOrdersByStatus(status));
        response.put("orderCounts", orderManagementService.getOrderCountByStatus());
        return new ResponseEntity( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{orderId}/status/{status}", method = RequestMethod.PUT )
    public ResponseEntity<Map> updateOrdersByStatus(@PathVariable String orderId, @PathVariable(value = "status") String status ) {
        Order order = orderManagementService.getOrderByOrderId( orderId );
        OrderStatus stat = OrderStatus.PENDING;
        switch(status) {
            case "pending":
                stat = OrderStatus.PENDING;
                break;
            case "confirmed":
                stat = OrderStatus.CONFIRMED;
                break;
            case "shipped":
                stat = OrderStatus.SHIPPED;
                break;
            case "delivered":
                stat = OrderStatus.DELIVERED;
                break;
            case "payment_received":
                stat = OrderStatus.PAYMENT_RECEIVED;
                break;
        }
        order.setOrderStatus( stat );
        orderManagementService.saveOrder(order);
        return new ResponseEntity( HttpStatus.OK );
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<?> getOrder( @PathVariable String orderId ){
        return new ResponseEntity(orderManagementService.convertEntityToDto(
                orderManagementService.getOrderByOrderId( orderId )
        ), HttpStatus.OK);
    }

//    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
//    public ResponseEntity<?> getPaymentTransaction( @RequestParam(value = "query", defaultValue = "") String query,
//                                       @RequestParam(value = "page", defaultValue = "0") Integer page,
//                                       @RequestParam(value = "limit", defaultValue = "10") Integer limit ){
//        Pageable pageable = PageRequest.of( page, limit );
//        Page<UserDto> users = orderManagementService.getPaymentTransaction(query, pageable);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("data", users.getContent());
//        response.put("currentPage", users.getNumber());
//        response.put("totalItems", users.getTotalElements());
//        response.put("totalPages", users.getTotalPages());
//        return new ResponseEntity(response , HttpStatus.OK );
//    }
}
