package com.project.inventory.store.shipping.repository;

import com.project.inventory.store.shipping.model.ShippingFee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingFeeRepository extends JpaRepository<ShippingFee, Integer> {

}
