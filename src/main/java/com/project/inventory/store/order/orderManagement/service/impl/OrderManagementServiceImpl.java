package com.project.inventory.store.order.orderManagement.service.impl;

import com.project.inventory.store.cart.cartItem.model.CartItem;
import com.project.inventory.store.cart.cartItem.service.CartItemService;
import com.project.inventory.customer.address.model.CustomerAddress;
import com.project.inventory.customer.address.model.CustomerAddressDto;
import com.project.inventory.customer.address.service.CustomerAddressService;
import com.project.inventory.customer.payment.model.PaymentMethod;
import com.project.inventory.customer.payment.model.PaymentMethodDto;
import com.project.inventory.customer.payment.service.PaymentMethodService;
import com.project.inventory.exception.invalid.order.OrderInvalidException;
import com.project.inventory.store.order.orderItem.model.OrderItem;
import com.project.inventory.store.order.orderItem.service.OrderItemService;
import com.project.inventory.store.order.orderManagement.model.OrderStatus;
import com.project.inventory.store.order.orderManagement.model.Order;
import com.project.inventory.store.order.orderManagement.model.OrderDto;
import com.project.inventory.store.order.orderManagement.repository.OrderManagementRepository;
import com.project.inventory.store.order.orderManagement.service.OrderManagementService;
import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.common.persmision.model.AccountDto;
import com.project.inventory.common.persmision.service.AccountService;
import com.project.inventory.common.persmision.service.AuthenticatedUser;
import com.project.inventory.store.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "shoppingOrderServiceImpl")
public class OrderManagementServiceImpl implements OrderManagementService {
    Logger logger = LoggerFactory.getLogger(OrderManagementServiceImpl.class);

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
    public void placeOrder(int customerAddressId,
                                    int paymentId,
                                    List<CartItem> cartItems){

        logger.info("{}", customerAddressId +" "+ paymentId + "\n" + cartItems);

        //get customer address
        CustomerAddress customerAddress = getCustomerAddress(customerAddressId);

        // get payment method
        PaymentMethod paymentMethod = getPaymentMethod(paymentId);

        List<OrderItem> orderItems = new ArrayList<>();

        if(customerAddress != null && paymentMethod != null){
            Order savedOrder = getAllRequiredInformation(customerAddress, paymentMethod, cartItems);

            for (CartItem cartItem : cartItems){

                OrderItem orderItem = new OrderItem( cartItem.getQuantity(),
                        cartItem.getAmount(),
                        productService.getProductById(cartItem.getProduct().getId()),
                        savedOrder);

                orderItems.add(orderItem);

            }
            orderItemService.saveOrderItem(orderItems);
            emptyCart(cartItems);
        }else {
            throw new OrderInvalidException("Placing orders Unsuccessfully. Please Try Again");
        }
    }

    @Override
    public List<OrderDto> getOrders() {
        List<OrderDto> shoppingOrders = new ArrayList<>();

        for (Order savedOrder : orderManagementRepository.findAllByStatusPending()){
            OrderDto orderDto = new OrderDto();

            orderDto.setId(savedOrder.getId());
            orderDto.setOrderStatus(savedOrder.getOrderStatus());
            orderDto.setTotalAmount(savedOrder.getTotalAmount());
            orderDto.setOrderedAt(savedOrder.getOrderedAt());
            orderDto.setDeliveredAt(savedOrder.getDeliveredAt());
            orderDto.setAccount(mapper.map(savedOrder.getAccount(), AccountDto.class));
            orderDto.setPaymentMethod(mapper.map(savedOrder.getPaymentMethod(), PaymentMethodDto.class));
            orderDto.setCustomerAddress(mapper.map(savedOrder.getCustomerAddress(), CustomerAddressDto.class));
            orderDto.setOrderItem(orderItemService.convertOrderItemsToDto(savedOrder.getOrderItems()));

            shoppingOrders.add(orderDto);
        }

        return shoppingOrders;


    }

    @Override
    public OrderDto convertEntityToDto(Order order) {
        return mapper.map(order, OrderDto.class);
    }

    @Override
    public Order convertDtoToEntity(OrderDto orderDto) {
        return mapper.map(orderDto, Order.class);
    }

    public Order getAllRequiredInformation(CustomerAddress customerAddress, PaymentMethod paymentMethod, List<CartItem> cartItems){
        double totalAmount = 0.0;
        Order order = new Order();
        Account account = accountService.getAccountById(authenticatedUser.getUserDetails().getId());

        for (CartItem cartItem : cartItems){
            totalAmount += cartItem.getAmount();
        }

        order.setCustomerAddress(customerAddress);
        order.setPaymentMethod(paymentMethod);
        order.setAccount(account);
        order.setTotalAmount(totalAmount);
        order.setOrderStatus(OrderStatus.PENDING);
        return orderManagementRepository.save(order);

    }

    public void emptyCart(List<CartItem> cartItems){
        cartItemService.removeItems(cartItems);
    }

    public CustomerAddress getCustomerAddress(int customerAddressId){
        return customerAddressService.convertDtoToEntity(customerAddressService.getCustomerAddress(customerAddressId));
    }
    public PaymentMethod getPaymentMethod(int paymentId){
        return paymentMethodService.getPaymentMethodById(paymentId);
    }

}
