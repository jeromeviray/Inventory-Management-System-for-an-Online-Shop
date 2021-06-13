package com.project.inventory.customer.payment.service;

import com.project.inventory.customer.payment.model.PaymentMethod;

import java.util.List;

public interface PaymentMethodService {

    List<PaymentMethod> getPaymentMethods();
    PaymentMethod getPaymentMethodById(int id);
    PaymentMethod getPaymentMethodByName(String paymentMethodName);
    PaymentMethod savePaymentMethod(PaymentMethod paymentMethod);
}
