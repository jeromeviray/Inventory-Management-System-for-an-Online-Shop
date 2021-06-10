package com.project.inventory.customer.address.repository;

import com.project.inventory.customer.address.model.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Integer> {
}
