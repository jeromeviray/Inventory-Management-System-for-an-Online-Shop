package com.project.inventory.store.order.shoppingOrder.service.impl;

import com.project.inventory.store.cart.cartItem.model.CartItem;
import com.project.inventory.store.cart.cartItem.service.CartItemService;
import com.project.inventory.customer.address.model.CustomerAddress;
import com.project.inventory.customer.address.model.CustomerAddressDto;
import com.project.inventory.customer.address.service.CustomerAddressService;
import com.project.inventory.customer.payment.model.PaymentMethod;
import com.project.inventory.customer.payment.model.PaymentMethodDto;
import com.project.inventory.customer.payment.service.PaymentMethodService;
import com.project.inventory.exception.order.ShoppingOrderInvalidException;
import com.project.inventory.store.order.orderItems.model.OrderItem;
import com.project.inventory.store.order.orderItems.service.OrderItemService;
import com.project.inventory.store.order.shoppingOrder.model.OrderStatus;
import com.project.inventory.store.order.shoppingOrder.model.ShoppingOrder;
import com.project.inventory.store.order.shoppingOrder.model.ShoppingOrderDto;
import com.project.inventory.store.order.shoppingOrder.repository.ShoppingOrderRepository;
import com.project.inventory.store.order.shoppingOrder.service.ShoppingOrderService;
import com.project.inventory.permission.model.Account;
import com.project.inventory.permission.model.AccountDto;
import com.project.inventory.permission.service.AccountService;
import com.project.inventory.permission.service.AuthenticatedUser;
import com.project.inventory.store.product.service.ProductService;
import org.modelmapper.ModelMapper;
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
    private ProductService productService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ShoppingOrderRepository shoppingOrderRepository;
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
            ShoppingOrder savedShoppingOrder = getAllRequiredInformation(customerAddress, paymentMethod, cartItems);

            for (CartItem cartItem : cartItems){

                OrderItem orderItem = new OrderItem( cartItem.getQuantity(),
                        cartItem.getAmount(),
                        productService.getProductById(cartItem.getProduct().getId()),
                        savedShoppingOrder);

                orderItems.add(orderItem);

            }
            orderItemService.saveOrderItem(orderItems);
            emptyCart(cartItems);
        }else {
            throw new ShoppingOrderInvalidException("Placing orders Unsuccessfully. Please Try Again");
        }
    }

    @Override
    public List<ShoppingOrderDto> getOrders() {
        List<ShoppingOrderDto> shoppingOrders = new ArrayList<>();

        for (ShoppingOrder savedShoppingOrder : shoppingOrderRepository.findAllByStatusPending()){
            ShoppingOrderDto shoppingOrderDto = new ShoppingOrderDto();

            shoppingOrderDto.setId(savedShoppingOrder.getId());
            shoppingOrderDto.setOrderStatus(savedShoppingOrder.getOrderStatus());
            shoppingOrderDto.setTotalAmount(savedShoppingOrder.getTotalAmount());
            shoppingOrderDto.setOrderedAt(savedShoppingOrder.getOrderedAt());
            shoppingOrderDto.setDeliveredAt(savedShoppingOrder.getDeliveredAt());
            shoppingOrderDto.setAccount(mapper.map(savedShoppingOrder.getAccount(), AccountDto.class));
            shoppingOrderDto.setPaymentMethod(mapper.map(savedShoppingOrder.getPaymentMethod(), PaymentMethodDto.class));
            shoppingOrderDto.setCustomerAddress(mapper.map(savedShoppingOrder.getCustomerAddress(), CustomerAddressDto.class));
            shoppingOrderDto.setOrderItem(orderItemService.convertOrderItemsToDto(savedShoppingOrder.getOrderItems()));

            shoppingOrders.add(shoppingOrderDto);
        }

        return shoppingOrders;


    }

    @Override
    public ShoppingOrderDto convertEntityToDto(ShoppingOrder shoppingOrder) {
        return mapper.map(shoppingOrder, ShoppingOrderDto.class);
    }

    @Override
    public ShoppingOrder convertDtoToEntity(ShoppingOrderDto shoppingOrderDto) {
        return mapper.map(shoppingOrderDto, ShoppingOrder.class);
    }

    public ShoppingOrder getAllRequiredInformation(CustomerAddress customerAddress, PaymentMethod paymentMethod, List<CartItem> cartItems){
        double totalAmount = 0.0;
        ShoppingOrder shoppingOrder = new ShoppingOrder();
        Account account = accountService.getAccountById(authenticatedUser.getUserDetails().getId());

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
        return customerAddressService.convertDtoToEntity(customerAddressService.getCustomerAddress(customerAddressId));
    }
    public PaymentMethod getPaymentMethod(int paymentId){
        return paymentMethodService.getPaymentMethodById(paymentId);
    }

}
