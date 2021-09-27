package com.project.inventory.store.order.orderManagement.controller;

import com.project.inventory.api.payment.PaymongoAPI;
import com.project.inventory.store.inventory.service.impl.InventoryServiceImpl;
import com.project.inventory.store.order.orderManagement.model.Order;
import com.project.inventory.store.order.orderManagement.model.OrderResponse;
import com.project.inventory.store.order.orderManagement.model.PlaceOrder;
import com.project.inventory.store.order.orderManagement.model.OrderDto;
import com.project.inventory.store.order.orderManagement.service.OrderManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
