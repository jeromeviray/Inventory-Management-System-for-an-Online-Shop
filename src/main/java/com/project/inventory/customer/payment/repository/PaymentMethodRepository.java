package com.project.inventory.customer.payment.repository;

import com.project.inventory.customer.payment.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {

    @Query(value = "SELECT * FROM payment_method", nativeQuery = true)
    List<PaymentMethod> findAllPaymentMethod();

    PaymentMethod findById(int id);
    PaymentMethod findByPaymentMethod(String paymentMethod);
}
