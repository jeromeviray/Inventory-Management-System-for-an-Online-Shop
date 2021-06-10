package com.project.inventory.customer.payment.repository;

import com.project.inventory.customer.payment.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
}
