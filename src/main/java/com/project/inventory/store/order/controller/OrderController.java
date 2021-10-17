package com.project.inventory.store.order.controller;

import com.project.inventory.api.payment.PaymongoAPI;
import com.project.inventory.store.inventory.service.InventoryService;
import com.project.inventory.store.order.model.*;
import com.project.inventory.store.order.orderItem.model.OrderItem;
import com.project.inventory.store.order.service.OrderService;
import com.project.inventory.webSecurity.config.AppProperties;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping( value = "api/v1/orders" )
class OrderController {
    Logger logger = LoggerFactory.getLogger( OrderController.class );

    @Autowired
    private OrderService orderService;
    @Autowired
    private InventoryService inventoryService;

    private PaymongoAPI paymongoAPI = new PaymongoAPI();

    @Autowired
    AppProperties appProperties;

    @RequestMapping( value = "/checkout", method = RequestMethod.POST )
    public ResponseEntity<?> placeOrder( @RequestBody PlaceOrder placeOrder ) {
        OrderResponse response = new OrderResponse();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            Order order = orderService.placeOrder(
                    placeOrder.getCustomerAddressId(),
                    placeOrder.getPaymentId(),
                    placeOrder.getCartItems() );
            logger.info(order.getPaymentMethod().getPaymentMethod());
            if( Objects.equals( order.getPaymentMethod().getPaymentMethod(), "GCASH" ) ) {
                String successUrl = String.format("%s/%s/%s", appProperties.getHostName(), order.getOrderId(), "payment/success");
                String failedUrl = String.format("%s/%s/%s", appProperties.getHostName(), order.getOrderId(), "payment/failed");
                Map resp = this.paymongoAPI.generateSource( order.getTotalAmount(), "PHP",  successUrl, failedUrl );
                Map data = (Map ) resp.get("data");
                Map attributes = (Map) data.get("attributes");
                Map redirect = (Map) attributes.get("redirect");
                response.setRedirectUrl( ( String ) redirect.get("checkout_url") );
                order.setExternalReference( (String) data.get("id") );
                orderService.saveOrder( order );
            }
        } catch( Exception e ) {
            httpStatus = HttpStatus.BAD_REQUEST;
            response.setSuccess( false );
            response.setMessage( e.getMessage() );
            throw e;
        }
        return new ResponseEntity( response, httpStatus );
    }

    @PreAuthorize( "hasRole('ROLE_CUSTOMER') or hasRole('ROLE_USER')" )
    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<OrderDto> getOrdersByAccountId() {
        return new ResponseEntity( orderService.getOrdersByAccountId(), HttpStatus.OK );
    }

    @RequestMapping( value = "/status/{status}", method = RequestMethod.GET )
    public ResponseEntity<Map> getOrdersByStatus(@PathVariable(value = "status") String status ) {
        Map response = new HashMap();
        response.put("orders", orderService.getOrdersByStatus(status));
        response.put("orderCounts", orderService.getOrderCountByStatus());
        return new ResponseEntity( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{orderId}/status/{status}", method = RequestMethod.PUT )
    public ResponseEntity<Map> updateOrdersByStatus(@PathVariable String orderId, @PathVariable(value = "status") String status ) {
        Order order = orderService.getOrderByOrderId( orderId );
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
                order.setDeliveredAt( new Date() );
                stat = OrderStatus.DELIVERED;
                break;
        }
        if(status == "payment_received") {
            order.setPaymentStatus( 1 );
            order.setPaid_at( new Date() );
        } else {
            if(stat.equals( OrderStatus.CONFIRMED )){
                for( OrderItem orderItem : order.getOrderItems()){
                    inventoryService.updateStock( orderItem.getProduct().getId(), orderItem.getQuantity() );
                }
            }
            order.setOrderStatus( stat );
        }
        orderService.saveOrder(order);
        return new ResponseEntity( HttpStatus.OK );
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<?> getOrder( @PathVariable String orderId ){
        return new ResponseEntity(orderService.convertEntityToDto(
                orderService.getOrderByOrderId( orderId )
        ), HttpStatus.OK);  
    }


    @RequestMapping(value = "/{orderId}/paid/{status}", method = RequestMethod.PUT)
    public ResponseEntity<?> markOrderAsPaid( @PathVariable String orderId, @PathVariable String status ){
        Order order = orderService.getOrderByOrderId( orderId );
        int paymentStatus = 1;
        if(status == "failed") {
            paymentStatus = 2;
        }
        if(order.getPaymentStatus() > 0 ) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Order "+order.getOrderId()+ " was already completed.");
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
        order.setPaymentStatus( paymentStatus );
        order.setPaid_at( new Date() );
        order.setOrderStatus( OrderStatus.CONFIRMED );
        orderService.saveOrder( order );
        return new ResponseEntity(order, HttpStatus.OK);
    }

    @RequestMapping(value = "/payments", method = RequestMethod.GET)
    public ResponseEntity<?> getPaymentTransactions(@RequestParam( value = "query", defaultValue = "", required = false ) String query,
                                                    @RequestParam( value = "page", defaultValue = "0" ) Integer page,
                                                    @RequestParam( value = "limit", defaultValue = "0" ) Integer limit){
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of( page, limit );

        Page<Order> orders = orderService.getPaymentTransactions(query, pageable);
        response.put( "data", orders.getContent() );
        response.put( "currentPage", orders.getNumber() );
        response.put( "totalItems", orders.getTotalElements() );
        response.put( "totalPages", orders.getTotalPages() );
        return new ResponseEntity(response, HttpStatus.OK);
    }

//    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
//    public ResponseEntity<?> getPaymentTransaction( @RequestParam(value = "query", defaultValue = "") String query,
//                                       @RequestParam(value = "page", defaultValue = "0") Integer page,
//                                       @RequestParam(value = "limit", defaultValue = "10") Integer limit ){
//        Pageable pageable = PageRequest.of( page, limit );
//        Page<UserDto> users = orderService.getPaymentTransaction(query, pageable);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("data", users.getContent());
//        response.put("currentPage", users.getNumber());
//        response.put("totalItems", users.getTotalElements());
//        response.put("totalPages", users.getTotalPages());
//        return new ResponseEntity(response , HttpStatus.OK );
//    }
}
