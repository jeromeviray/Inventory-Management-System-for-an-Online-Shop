package com.project.inventory.customer.address.service;

import com.project.inventory.customer.address.model.CustomerAddress;

import java.util.List;

public interface CustomerAddressService {

    CustomerAddress saveCustomerAddress(CustomerAddress customerAddress);
    List<CustomerAddress> getCustomerAddresses();
    CustomerAddress getCustomerAddress(int id);
    CustomerAddress getCustomerAddressByAccountId(int accountId);
    CustomerAddress updateCustomerAddress(int id, CustomerAddress customerAddress);
    void deleteCustomerAddress(int id);
}
