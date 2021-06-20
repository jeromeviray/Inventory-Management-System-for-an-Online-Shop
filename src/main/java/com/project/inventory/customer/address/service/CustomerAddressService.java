package com.project.inventory.customer.address.service;

import com.project.inventory.customer.address.model.CustomerAddress;
import com.project.inventory.customer.address.model.CustomerAddressDto;

import java.util.List;

public interface CustomerAddressService {

    CustomerAddress saveCustomerAddress(CustomerAddress customerAddress);
    List<CustomerAddressDto> getCustomerAddresses();
    CustomerAddressDto getCustomerAddress(int id);
    CustomerAddressDto getCustomerAddressByAccountId(int accountId);
    CustomerAddress updateCustomerAddress(int id, CustomerAddress customerAddress);
    void deleteCustomerAddress(int id);
    CustomerAddressDto convertEntityToDto(CustomerAddress customerAddress);
    CustomerAddress convertDtoToEntity(CustomerAddressDto customerAddressDto);
}
