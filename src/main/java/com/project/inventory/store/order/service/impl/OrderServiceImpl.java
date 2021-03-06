package com.project.inventory.store.order.service.impl;

import com.project.inventory.common.permission.role.model.Role;
import com.project.inventory.common.sms.service.Sms;
import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.cart.cartItem.model.CartItemDto;
import com.project.inventory.store.cart.cartItem.service.CartItemService;
import com.project.inventory.customer.address.model.CustomerAddress;
import com.project.inventory.customer.address.service.CustomerAddressService;
import com.project.inventory.customer.payment.model.PaymentMethod;
import com.project.inventory.customer.payment.service.PaymentMethodService;
import com.project.inventory.exception.invalid.order.OrderInvalidException;
import com.project.inventory.store.inventory.service.InventoryService;
import com.project.inventory.store.order.orderItem.model.OrderItem;
import com.project.inventory.store.order.orderItem.service.OrderItemService;
import com.project.inventory.store.order.model.OrderStatus;
import com.project.inventory.store.order.model.Order;
import com.project.inventory.store.order.model.OrderDto;
import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.service.AccountService;
import com.project.inventory.common.permission.service.AuthenticatedUser;
import com.project.inventory.store.order.repository.OrderRepository;
import com.project.inventory.store.order.service.OrderService;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.promo.model.Promo;
import com.project.inventory.store.product.promo.model.PromoStatus;
import com.project.inventory.store.product.promo.service.PromoService;
import com.project.inventory.store.product.service.ProductService;
import com.project.inventory.store.shipping.model.ShippingFee;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    Logger logger = LoggerFactory.getLogger( OrderServiceImpl.class );

    @Autowired
    private CustomerAddressService customerAddressService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private PaymentMethodService paymentMethodService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AuthenticatedUser authenticatedUser;
    @Autowired
    private Sms sms;
    @Autowired
    private PromoService promoService;
    @Autowired
    private InventoryService inventoryService;

    @Override
    public Order placeOrder( int customerAddressId, int paymentId, List<CartItemDto> cartItems, ShippingFee shippingFee ) {
        try {
            //get customer address
            CustomerAddress customerAddress = getCustomerAddress( customerAddressId );

            // get payment method
            PaymentMethod paymentMethod = getPaymentMethod( paymentId );

            List<OrderItem> orderItems = new ArrayList<>();

            if ( customerAddress != null && paymentMethod != null ) {
                Order order = orderRepository.save( getAllRequiredInformation( customerAddress,
                        paymentMethod,
                        cartItems,
                        shippingFee ) );
                for ( CartItemDto cartItem : cartItems ) {
                    Product product = productService.getProductById( cartItem.getProduct().getProduct().getId() );

                    if ( product.getPromo() != null ) {
                        Promo promo = product.getPromo();
                        if ( promo.getStatus().equals( PromoStatus.ONGOING ) ) {
                            promoService.setSoldItems( promo.getId(), cartItem.getQuantity() );
                        }
                    }
                    double discount = promoService.getDiscount( product );
                    double price = product.getPrice() - discount;
                    double amount = cartItem.getQuantity() * price;
                    OrderItem orderItem = new OrderItem( cartItem.getQuantity(),
                            amount,
                            product,
                            order );
                    inventoryService.updateStock( cartItem.getProduct().getProduct().getId(), cartItem.getQuantity() );
                    orderItems.add( orderItem );

                }
                orderItemService.saveOrderItem( orderItems );
                emptyCart( cartItems );

                String message = "Your Order has been placed. Order id is " + order.getOrderId();
                String number = order.getCustomerAddress().getPhoneNumber().substring( 1 );
                logger.info( "{}", number );
                sms.sendSms( "+63"+number, message );
                return order;
            } else {
                logger.info( "Error has been occurs: " );
                throw new OrderInvalidException( "Placing orders Unsuccessfully. Please Try Again" );
            }
        } catch ( Exception e ) {
            logger.info( "Error: {}", e.getMessage() );
            throw e;
        }
    }

    @Override
    public List<OrderDto> getOrdersByAccountId() {
        List<OrderDto> orders = new ArrayList<>();
        Account account = authenticatedUser.getUserDetails();
        for ( Order savedOrder : orderRepository.findAllByAccountId( account.getId() ) ) {
            orders.add( convertEntityToDto( savedOrder ) );
        }
        return orders;
    }

    @Override
    public Page<OrderDto> getOrdersByStatus( String status, String query, Pageable pageable ) {
        // retrieve all the pending orders dynamically based on the role
        // with role customer or user they just retrieve their own order data

        List<OrderDto> orders = new ArrayList<>();
        Account account = authenticatedUser.getUserDetails();

        String permission = getRoles( account.getRoles() );
        if ( permission.equals( "ROLE_SUPER_ADMIN" ) || permission.equals( "ROLE_ADMIN" ) ) {
            return getOrders( status, query, pageable );
        } else if ( permission.equals( "ROLE_USER" ) || permission.equals( "ROLE_CUSTOMER" ) ) {
            return getCustomerOrders( status, account.getId(), query, pageable );
        } else {
            throw new AccessDeniedException( "ACCESS DENIED" );
        }
    }

    @Override
    public Map<String, BigInteger> getOrderCountByStatus() {
        Account account = authenticatedUser.getUserDetails();
        String permission = getRoles( account.getRoles() );
        List<Object[]> counts = new ArrayList();

        Integer accountId = 0;
        if ( permission.equals( "ROLE_SUPER_ADMIN" ) || permission.equals( "ROLE_ADMIN" ) ) {
            counts = orderRepository.getOrderCountGroupBy();
        } else if ( permission.equals( "ROLE_USER" ) || permission.equals( "ROLE_CUSTOMER" ) ) {
            accountId = account.getId();
            counts = orderRepository.getCustomerOrderCountGroupBy( accountId );
        } else {
            throw new AccessDeniedException( "ACCESS DENIED" );
        }

        Map<String, BigInteger> totals = new HashMap<>();
        for ( Object[] result : counts ) {
            totals.put( result[ 0 ].toString(), ( BigInteger ) result[ 1 ] );
        }
        if ( accountId > 0 ) {
            BigInteger delivered = totals.get( "DELIVERED" );
            BigInteger paymentReceived = totals.get( "PAYMENT_RECEIVED" );
            BigInteger total = null;
            if ( delivered == null ) {
                delivered = new BigInteger( "0" );
            }
            if ( paymentReceived == null ) {
                paymentReceived = new BigInteger( "0" );
            } else {
                totals.remove( "PAYMENT_RECEIVED" );
            }
            totals.put( "DELIVERED", paymentReceived.add( delivered ) );
        }
        return totals;
    }

    @Override
    public Order getOrderByOrderId( String orderId ) {
        return orderRepository.findByOrderId( orderId )
                .orElseThrow( () -> new NotFoundException( "No Order Found" ) );
    }

    @Override
    public OrderDto convertEntityToDto( Order order ) {
        return mapper.map( order, OrderDto.class );
    }

    @Override
    public Order convertDtoToEntity( OrderDto orderDto ) {
        return mapper.map( orderDto, Order.class );
    }

    public Order getAllRequiredInformation( CustomerAddress customerAddress,
                                            PaymentMethod paymentMethod,
                                            List<CartItemDto> cartItems,
                                            ShippingFee shippingFee ) {
        double totalAmount = 0.0;
        Order order = new Order();
        Account account = accountService.getAccountById( authenticatedUser.getUserDetails().getId() );
        UUID uuid = UUID.randomUUID();
        String orderId = uuid.toString();
        for ( CartItemDto cartItem : cartItems ) {
            Product product = productService.getProductById( cartItem.getProduct().getProduct().getId() );
            double discount = promoService.getDiscount( product );
            double price = product.getPrice() - discount;
            double amount = cartItem.getQuantity() * price;
            totalAmount += amount;
        }

        order.setOrderId( orderId );
        order.setCustomerAddress( customerAddress );
        order.setPaymentMethod( paymentMethod );
        order.setAccount( account );
        order.setShippingFee( shippingFee );
        order.setTotalAmount( totalAmount + shippingFee.getShippingAmount() );
        order.setOrderStatus( OrderStatus.PENDING );
        order.setPaymentStatus( 0 );
        return order;
    }

    public void emptyCart( List<CartItemDto> cartItems ) {
        cartItemService.removeItems( cartItems );
    }

    public CustomerAddress getCustomerAddress( int customerAddressId ) {
        return customerAddressService.convertDtoToEntity( customerAddressService.getCustomerAddress( customerAddressId ) );
    }

    public PaymentMethod getPaymentMethod( int paymentId ) {
        return paymentMethodService.getPaymentMethodById( paymentId );
    }

    private String getRoles( Set<Role> roles ) {
        List<String> getRolesName = new ArrayList<>();
        for ( Role role : roles ) {
            getRolesName.add( "ROLE_" + role.getRoleName() );
        }
        return getRolesName.get( 0 );
    }

    private Page<OrderDto> getCustomerOrders( String status, int id, String query, Pageable pageable ) {
//        List<OrderDto> orders = new ArrayList<>();
        List<String> statuses = new ArrayList<>();
        statuses.add( status );
        if ( Objects.equals( status, "delivered" ) ) {
            statuses.add( "payment_received" );
        }
        List<OrderDto> orderRecordByPages = new ArrayList<>();
        Page<Order> orders = orderRepository.findAllByOrderStatusAndAccountId( statuses, id, query, pageable );

        for ( Order order : orders.getContent() ) {
            orderRecordByPages.add( convertEntityToDto( order ) );
        }
        return new PageImpl<>( orderRecordByPages, pageable, orders.getTotalElements() );
    }

    private Page<OrderDto> getOrders( String status, String query, Pageable pageable ) {

        List<OrderDto> orderRecordByPages = new ArrayList<>();
        Page<Order> orders = orderRepository.findAllByOrderStatus( status, query, pageable );

        for ( Order order : orders.getContent() ) {
            orderRecordByPages.add( convertEntityToDto( order ) );
        }
        return new PageImpl<>( orderRecordByPages, pageable, orders.getTotalElements() );
    }

    @Override
    public void saveOrder( Order order ) {
        orderRepository.save( order );
    }

    @Override
    public Page<Order> getPaymentTransactions( String query, Pageable pageable ) {
        return orderRepository.getPaymentTransactions( pageable );
    }

}
