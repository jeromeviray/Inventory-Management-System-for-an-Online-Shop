package com.project.inventory.store.order.orderManagement.service.impl;

import com.project.inventory.common.permission.role.model.Role;
import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.cart.cartItem.model.CartItem;
import com.project.inventory.store.cart.cartItem.service.CartItemService;
import com.project.inventory.customer.address.model.CustomerAddress;
import com.project.inventory.customer.address.service.CustomerAddressService;
import com.project.inventory.customer.payment.model.PaymentMethod;
import com.project.inventory.customer.payment.service.PaymentMethodService;
import com.project.inventory.exception.invalid.order.OrderInvalidException;
import com.project.inventory.store.order.orderItem.model.OrderItem;
import com.project.inventory.store.order.orderItem.service.OrderItemService;
import com.project.inventory.store.order.orderManagement.model.OrderStatus;
import com.project.inventory.store.order.orderManagement.model.Order;
import com.project.inventory.store.order.orderManagement.model.OrderDto;
import com.project.inventory.store.order.orderManagement.repository.OrderManagementRepository;
import com.project.inventory.store.order.orderManagement.service.OrderManagementService;
import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.service.AccountService;
import com.project.inventory.common.permission.service.AuthenticatedUser;
import com.project.inventory.store.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service( value = "shoppingOrderServiceImpl" )
public class OrderManagementServiceImpl implements OrderManagementService {
    Logger logger = LoggerFactory.getLogger( OrderManagementServiceImpl.class );

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
    private OrderManagementRepository orderManagementRepository;
    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Override
    public void placeOrder( int customerAddressId,
                            int paymentId,
                            List<CartItem> cartItems ) {

        try {
            //get customer address
            CustomerAddress customerAddress = getCustomerAddress( customerAddressId );

            // get payment method
            PaymentMethod paymentMethod = getPaymentMethod( paymentId );

            List<OrderItem> orderItems = new ArrayList<>();

            if ( customerAddress != null && paymentMethod != null ) {
                Order order = orderManagementRepository.save( getAllRequiredInformation( customerAddress,
                        paymentMethod,
                        cartItems ) );
                emptyCart( cartItems );
                for ( CartItem cartItem : cartItems ) {

                    OrderItem orderItem = new OrderItem( cartItem.getQuantity(),
                            cartItem.getAmount(),
                            productService.getProductById( cartItem.getProduct().getId() ),
                            order );

                    orderItems.add( orderItem );

                }
                orderItemService.saveOrderItem( orderItems );
            } else {
                logger.info( "Error has been occurs: " );
                throw new OrderInvalidException( "Placing orders Unsuccessfully. Please Try Again" );
            }
        } catch ( Exception e ) {
            logger.info( "Error :{}", e.getMessage() );
            throw e;
        }

    }

    @Override
    public List<OrderDto> getOrdersByAccountId() {
        List<OrderDto> orders = new ArrayList<>();
        Account account = authenticatedUser.getUserDetails();
        for ( Order savedOrder : orderManagementRepository.findAllByAccountId( account.getId() ) ) {
            orders.add( convertEntityToDto( savedOrder ) );
        }
        return orders;
    }

    @Override
    public List<OrderDto> getPendingOrders() {
        // retrieve all the pending orders dynamically based on the role
        // with role customer or user they just retrieve their own order data

        List<OrderDto> orders = new ArrayList<>();
        Account account = authenticatedUser.getUserDetails();

        String permission = getRoles( account.getRoles() );
        if ( permission.equals( "ROLE_SUPER_ADMIN" ) || permission.equals( "ROLE_ADMIN" ) ) {
            return getOrders( OrderStatus.PENDING.name() );

        } else if ( permission.equals( "ROLE_USER" ) || permission.equals( "ROLE_CUSTOMER" ) ) {
            return getCustomerOrders( OrderStatus.PENDING.name(), account.getId() );

        } else {
            throw new AccessDeniedException( "ACCESS DENIED" );
        }
    }

    @Override
    public List<OrderDto> getConfirmedOrders() {
        List<OrderDto> orders = new ArrayList<>();

        Account account = authenticatedUser.getUserDetails();

        String permission = getRoles( account.getRoles() );
        if ( permission.equals( "ROLE_SUPER_ADMIN" ) || permission.equals( "ROLE_ADMIN" ) ) {
            return getOrders( OrderStatus.CONFIRMED.name() );

        } else if ( permission.equals( "ROLE_USER" ) || permission.equals( "ROLE_CUSTOMER" ) ) {
            return getCustomerOrders( OrderStatus.CONFIRMED.name(), account.getId() );

        } else {
            throw new AccessDeniedException( "ACCESS DENIED" );
        }
    }

    @Override
    public List<OrderDto> getCompletedOrders() {
        List<OrderDto> orders = new ArrayList<>();

        Account account = authenticatedUser.getUserDetails();

        String permission = getRoles( account.getRoles() );
        if ( permission.equals( "ROLE_SUPER_ADMIN" ) || permission.equals( "ROLE_ADMIN" ) ) {
            return getOrders( OrderStatus.COMPLETED.name() );

        } else if ( permission.equals( "ROLE_USER" ) || permission.equals( "ROLE_CUSTOMER" ) ) {
            return getCustomerOrders( OrderStatus.COMPLETED.name(), account.getId() );

        } else {
            throw new AccessDeniedException( "ACCESS DENIED" );
        }
    }

    @Override
    public Order getOrderByOrderId( String orderId ) {
        return  orderManagementRepository.findByOrderId( orderId )
                .orElseThrow(() -> new NotFoundException("No Order Found") );
    }

    @Override
    public OrderDto convertEntityToDto( Order order ) {
        return mapper.map( order, OrderDto.class );
    }

    @Override
    public Order convertDtoToEntity( OrderDto orderDto ) {
        return mapper.map( orderDto, Order.class );
    }

    public Order getAllRequiredInformation( CustomerAddress customerAddress, PaymentMethod paymentMethod, List<CartItem> cartItems ) {
        double totalAmount = 0.0;
        Order order = new Order();
        Account account = accountService.getAccountById( authenticatedUser.getUserDetails().getId() );
        UUID uuid = UUID.randomUUID();
        String orderId = uuid.toString();

        for ( CartItem cartItem : cartItems ) {
            totalAmount += cartItem.getAmount();
        }

        order.setOrderId( orderId );
        order.setCustomerAddress( customerAddress );
        order.setPaymentMethod( paymentMethod );
        order.setAccount( account );
        order.setTotalAmount( totalAmount );
        order.setOrderStatus( OrderStatus.PENDING );
        return order;
    }

    public void emptyCart( List<CartItem> cartItems ) {
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

    private List<OrderDto> getCustomerOrders( String status, int id ) {
        List<OrderDto> orders = new ArrayList<>();
        for ( Order savedOrder : orderManagementRepository.findAllByOrderStatusAndAccountId( status, id ) ) {
            orders.add( convertEntityToDto( savedOrder ) );
        }
        return orders;
    }

    private List<OrderDto> getOrders( String status ) {
        List<OrderDto> orders = new ArrayList<>();
        for ( Order savedOrder : orderManagementRepository.findAllByOrderStatus( status ) ) {
            orders.add( convertEntityToDto( savedOrder ) );
        }
        return orders;
    }
}
