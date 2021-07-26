package com.project.inventory.customer.address.repository;

import com.project.inventory.customer.address.model.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Integer> {

    @Query(value = "SELECT * FROM customer_address_info", nativeQuery = true)
    List<CustomerAddress> findAllCustomerAddresses();

    Optional<CustomerAddress> findById(int id);

    @Query(value = "SELECT * FROM customer_address_info WHERE is_default = 1 AND account_id = :accountId", nativeQuery = true)
    CustomerAddress findByAccountId(int accountId);

//    @Query(value = "SELECT * FROM customer_address_info WHERE is_default = 1", nativeQuery = true)
//    CustomerAddress findByDefault();
}
