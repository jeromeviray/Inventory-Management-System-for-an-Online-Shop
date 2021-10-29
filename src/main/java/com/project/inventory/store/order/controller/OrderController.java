package com.project.inventory.store.order.controller;

import com.project.inventory.api.payment.PaymongoAPI;
import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.service.AuthenticatedUser;
import com.project.inventory.common.sms.service.impl.SmsImpl;
import com.project.inventory.store.cart.cartItem.model.CartItem;
import com.project.inventory.store.cart.cartItem.model.CartItemDto;
import com.project.inventory.store.cart.cartItem.repository.CartItemRepository;
import com.project.inventory.store.inventory.model.InventoryDto;
import com.project.inventory.store.inventory.service.InventoryService;
import com.project.inventory.store.order.model.*;
import com.project.inventory.store.order.orderItem.model.OrderItem;
import com.project.inventory.store.order.service.OrderService;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.model.ProductAndInventoryDto;
import com.project.inventory.store.product.model.ProductDto;
import com.project.inventory.store.shipping.service.ShippingFeeService;
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

import java.util.*;

@RestController
@RequestMapping( value = "api/v1/orders" )
class OrderController {
    Logger logger = LoggerFactory.getLogger( OrderController.class );
    @Autowired
    AppProperties appProperties;
    @Autowired
    private OrderService orderService;
    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ShippingFeeService shippingFeeService;

    @Autowired
    private AuthenticatedUser authenticatedUser;
    @Autowired
    private SmsImpl sms;

    private PaymongoAPI paymongoAPI = new PaymongoAPI();

    @RequestMapping( value = "/checkout", method = RequestMethod.POST )
    public ResponseEntity<?> placeOrder( @RequestBody PlaceOrder placeOrder ) {
        OrderResponse response = new OrderResponse();
        HttpStatus httpStatus = HttpStatus.OK;
        try {

            Account account = authenticatedUser.getUserDetails();

            CheckoutOrderValidate checkoutOrderValidate = new CheckoutOrderValidate();
            checkoutOrderValidate.setAccountId( account.getId() );
            checkoutOrderValidate.setCartId( placeOrder.getCartId() );
            checkoutOrderValidate.setItems( placeOrder.getCartItems() );

            Map<String, Object> rs = this.validateCheckoutErrors( checkoutOrderValidate );
            if( (Boolean ) rs.get("is_invalidate")) {
                return new ResponseEntity<>( rs, HttpStatus.BAD_REQUEST );
            }

            Order order = orderService.placeOrder(
                    placeOrder.getCustomerAddressId(),
                    placeOrder.getPaymentId(),
                    placeOrder.getCartItems(),
                    shippingFeeService.getShippingFeeById( placeOrder.getShippingFeeId() ) );

            if ( Objects.equals( order.getPaymentMethod().getPaymentMethod(), "GCASH" ) ) {
                String successUrl = String.format( "%s/cart/%s/%s", appProperties.getHostName(), order.getOrderId(), "payment/success" );
                String failedUrl = String.format( "%s/cart/%s/%s", appProperties.getHostName(), order.getOrderId(), "payment/failed" );
                Map resp = this.paymongoAPI.generateSource( order.getTotalAmount(), "PHP", successUrl, failedUrl );
                Map data = ( Map ) resp.get( "data" );
                Map attributes = ( Map ) data.get( "attributes" );
                Map redirect = ( Map ) attributes.get( "redirect" );
                response.setRedirectUrl( ( String ) redirect.get( "checkout_url" ) );
                order.setExternalReference( ( String ) data.get( "id" ) );
                orderService.saveOrder( order );
            }
        } catch ( Exception e ) {
            httpStatus = HttpStatus.BAD_REQUEST;
            response.setSuccess( false );
            response.setMessage( e.getMessage() );
            throw e;
        }
        return new ResponseEntity<>( response, httpStatus );
    }

    @PreAuthorize( "hasRole('ROLE_CUSTOMER') or hasRole('ROLE_USER')" )
    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<?> getOrdersByAccountId() {
        return new ResponseEntity<>( orderService.getOrdersByAccountId(), HttpStatus.OK );
    }

    @RequestMapping( value = "/status/{status}", method = RequestMethod.GET )
    public ResponseEntity<?> getOrdersByStatus( @PathVariable( value = "status" ) String status,
                                                @RequestParam( value = "query", defaultValue = "", required = false ) String query,
                                                @RequestParam( value = "page", defaultValue = "0" ) Integer page,
                                                @RequestParam( value = "limit", defaultValue = "0" ) Integer limit ) {
//        Map<String, Object> response = new HashMap();
//        response.put( "orders", orderService.getOrdersByStatus( status, query ) );
//        response.put( "orderCounts", orderService.getOrderCountByStatus() );
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of( page, limit );
        Page<OrderDto> orders = orderService.getOrdersByStatus( status, query, pageable ) ;

        response.put( "data", orders.getContent() );
        response.put( "currentPage", orders.getNumber() );
        response.put( "totalItems", orders.getTotalElements() );
        response.put( "totalPages", orders.getTotalPages() );
        response.put( "orderCounts", orderService.getOrderCountByStatus() );

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{orderId}/status/{status}", method = RequestMethod.PUT )
    public ResponseEntity<Map> updateOrdersByStatus( @PathVariable String orderId,
                                                     @PathVariable( value = "status" ) String status,
                                                     @RequestParam( value = "trackingNumber", required = false ) String trackingNumber,
                                                     @RequestParam( value = "trackingUrl", required = false ) String trackingUrl ) {
        Order order = orderService.getOrderByOrderId( orderId );
        OrderStatus stat = OrderStatus.PENDING;
        switch ( status ) {
            case "pending":
                stat = OrderStatus.PENDING;
                break;
            case "confirmed":
                stat = OrderStatus.CONFIRMED;
                break;
            case "shipped":
                stat = OrderStatus.SHIPPED;
                order.setTrackingNumber( trackingNumber );
                order.setTrackingUrl( trackingUrl );
                break;
            case "delivered":
                order.setDeliveredAt( new Date() );
                stat = OrderStatus.DELIVERED;
                break;
            case "request_refund":
                stat = OrderStatus.REQUEST_REFUND;
                break;

        }
        if ( status.equals( "payment_received" ) ) {
            order.setPaymentStatus( 1 );
            order.setPaid_at( new Date() );
        } else if ( status.equals( "cancel" ) ) {
            order.setOrderStatus( OrderStatus.CANCELLED );
            for ( OrderItem item : order.getOrderItems() ) {
                inventoryService.cancelStock( item.getProduct().getId(), item.getQuantity() );
            }
        } else if ( status.equals( "accept_refund" ) ) {
            order.setPaymentStatus( 3 );
            order.setRefundAt( new Date() );
            order.setOrderStatus( OrderStatus.REFUNDED );
            for ( OrderItem item : order.getOrderItems() ) {
                inventoryService.cancelStock( item.getProduct().getId(), item.getQuantity() );
            }
        } else {
            order.setOrderStatus( stat );
        }
        String message = "Your Order has been "+status+". Order id is " + order.getOrderId();
        String number = order.getCustomerAddress().getPhoneNumber().substring( 1 );
        sms.sendSms( "+63"+number, message );
        orderService.saveOrder( order );
        return new ResponseEntity( HttpStatus.OK );
    }

    @RequestMapping( value = "/{orderId}", method = RequestMethod.GET )
    public ResponseEntity<?> getOrder( @PathVariable String orderId ) {
        return new ResponseEntity( orderService.convertEntityToDto(
                orderService.getOrderByOrderId( orderId )
        ), HttpStatus.OK );
    }


    @RequestMapping( value = "/{orderId}/paid/{status}", method = RequestMethod.PUT )
    public ResponseEntity<?> markOrderAsPaid( @PathVariable String orderId, @PathVariable String status ) {
        Order order = orderService.getOrderByOrderId( orderId );
        Integer paymentStatus = 1;
        if ( Objects.equals( status, "failed" ) ) {
            paymentStatus = 2;
        }
        Integer currentPaymentStatus = order.getPaymentStatus();
        if ( currentPaymentStatus != null && currentPaymentStatus > 0 ) {
            Map<String, Object> response = new HashMap<>();
            response.put( "message", "Order " + order.getOrderId() + " was already completed." );
            return new ResponseEntity( response, HttpStatus.OK );
        }
        order.setPaymentStatus( paymentStatus );
        order.setPaid_at( new Date() );
        order.setOrderStatus( OrderStatus.CONFIRMED );
        orderService.saveOrder( order );
        return new ResponseEntity( order, HttpStatus.OK );
    }

    @RequestMapping( value = "/payments", method = RequestMethod.GET )
    public ResponseEntity<?> getPaymentTransactions( @RequestParam( value = "query", defaultValue = "", required = false ) String query,
                                                     @RequestParam( value = "page", defaultValue = "0" ) Integer page,
                                                     @RequestParam( value = "limit", defaultValue = "0" ) Integer limit ) {
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of( page, limit );

        Page<Order> orders = orderService.getPaymentTransactions( query, pageable );
        response.put( "data", orders.getContent() );
        response.put( "currentPage", orders.getNumber() );
        response.put( "totalItems", orders.getTotalElements() );
        response.put( "totalPages", orders.getTotalPages() );
        return new ResponseEntity( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/validate", method = RequestMethod.POST )
    public ResponseEntity<?> validateStock( @RequestBody CheckoutOrderValidate checkoutOrderValidate ) {
        Map<String, Object> response = this.validateCheckoutErrors( checkoutOrderValidate );
        return new ResponseEntity( response, HttpStatus.OK );
    }


    private Map<String, Object> validateCheckoutErrors(CheckoutOrderValidate checkoutOrderValidate) {
        Map<String, Object> response = new HashMap<>();
        List<String> errorMessages = new ArrayList<>();

        for ( CartItemDto item : checkoutOrderValidate.getItems() ) {
            ProductDto product = item.getProduct().getProduct();

            Product prod = new Product();
            prod.setId( product.getId() );

            Integer totalStock = inventoryService.getTotalStocks( prod );

            if ( item.getQuantity() > totalStock ) {
                errorMessages.add( String.format( "Insufficient available quantity for product %s", product.getProductName() ) );
            }
        }
        response.put( "error_messages", errorMessages );
        response.put( "is_invalidate", errorMessages.size() > 0 );
        return  response;
    }


}
