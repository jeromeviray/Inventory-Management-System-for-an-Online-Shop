package com.project.inventory.order.shoppingOrder.service.impl;

import com.project.inventory.cart.cartItem.model.CartItem;
import com.project.inventory.cart.cartItem.service.CartItemService;
import com.project.inventory.customer.address.model.CustomerAddress;
import com.project.inventory.customer.address.service.CustomerAddressService;
import com.project.inventory.customer.payment.model.PaymentMethod;
import com.project.inventory.customer.payment.service.PaymentMethodService;
import com.project.inventory.exception.order.ShoppingOrderInvalidException;
import com.project.inventory.order.orderItems.model.OrderItem;
import com.project.inventory.order.orderItems.service.OrderItemService;
import com.project.inventory.order.shoppingOrder.model.OrderStatus;
import com.project.inventory.order.shoppingOrder.model.ShoppingOrder;
import com.project.inventory.order.shoppingOrder.repository.ShoppingOrderRepository;
import com.project.inventory.order.shoppingOrder.service.ShoppingOrderService;
import com.project.inventory.permission.model.Account;
import com.project.inventory.permission.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "shoppingOrderServiceImpl")
public class ShoppingOrderServiceImpl implements ShoppingOrderService {
    Logger logger = LoggerFactory.getLogger(ShoppingOrderServiceImpl.class);

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
    private ShoppingOrderRepository shoppingOrderRepository;

    int tempId = 1;

    @Override
    public ShoppingOrder placeOrder(int customerAddressId,
                                    int paymentId,
                                    List<CartItem> cartItems) {
        CustomerAddress customerAddress = getCustomerAddress(customerAddressId);

        PaymentMethod paymentMethod = getPaymentMethod(paymentId);
        List<OrderItem> orderItems = new ArrayList<>();

        if(customerAddress != null && paymentMethod != null){
            ShoppingOrder savedShoppingOrder = getAllRequiredInformation(customerAddress, paymentMethod, cartItems);
            for (CartItem cartItem : cartItems){
                OrderItem orderItem = new OrderItem(cartItem.getQuantity(), cartItem.getAmount(), cartItem.getProduct(), savedShoppingOrder);
                orderItems.add(orderItem);
            }
            orderItemService.saveOrderItem(orderItems);
            emptyCart(cartItems);
        }
        throw new ShoppingOrderInvalidException("Placing Order Unsuccessfully. Please Try again!");
    }

    public ShoppingOrder getAllRequiredInformation(CustomerAddress customerAddress, PaymentMethod paymentMethod, List<CartItem> cartItems){
        double totalAmount = 0.0;
        ShoppingOrder shoppingOrder = new ShoppingOrder();
        Account account = accountService.getAccountById(tempId);

        for (CartItem cartItem : cartItems){
            totalAmount += cartItem.getAmount();
        }

        shoppingOrder.setCustomerAddress(customerAddress);
        shoppingOrder.setPaymentMethod(paymentMethod);
        shoppingOrder.setAccount(account);
        shoppingOrder.setTotalAmount(totalAmount);
        shoppingOrder.setOrderStatus(OrderStatus.PENDING);
        return shoppingOrderRepository.save(shoppingOrder);

    }

    public void emptyCart(List<CartItem> cartItems){
        cartItemService.removeItems(cartItems);
    }

    public CustomerAddress getCustomerAddress(int customerAddressId){
        return customerAddressService.getCustomerAddress(customerAddressId);
    }
    public PaymentMethod getPaymentMethod(int paymentId){
        return paymentMethodService.getPaymentMethodById(paymentId);
    }

}
