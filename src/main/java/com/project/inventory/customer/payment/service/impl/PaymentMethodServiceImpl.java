package com.project.inventory.customer.payment.service.impl;

import com.project.inventory.customer.payment.model.PaymentMethod;
import com.project.inventory.customer.payment.repository.PaymentMethodRepository;
import com.project.inventory.customer.payment.service.PaymentMethodService;
import com.project.inventory.exception.paymentMethod.PaymentMethodNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "paymentMethodServiceImpl")
public class PaymentMethodServiceImpl implements PaymentMethodService {
    Logger logger = LoggerFactory.getLogger(PaymentMethodServiceImpl.class);

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    @Override
    public PaymentMethod getPaymentMethodById(int id) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id);
        if(paymentMethod == null) throw new PaymentMethodNotFoundException("Payment Method Not Found.");

        return paymentMethod;
    }

    @Override
    public PaymentMethod getPaymentMethodByName(String paymentMethodName) {
        PaymentMethod paymentMethod = paymentMethodRepository.findByPaymentMethod(paymentMethodName);
        if(paymentMethod == null) throw new PaymentMethodNotFoundException("Payment Method Not Found.");

        return paymentMethod;
    }

    @Override
    public PaymentMethod savePaymentMethod(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }
}
