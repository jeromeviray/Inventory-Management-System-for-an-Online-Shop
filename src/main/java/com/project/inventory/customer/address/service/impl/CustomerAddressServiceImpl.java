package com.project.inventory.customer.address.service.impl;

import com.project.inventory.customer.address.model.CustomerAddress;
import com.project.inventory.customer.address.repository.CustomerAddressRepository;
import com.project.inventory.customer.address.service.CustomerAddressService;
import com.project.inventory.exception.customer.CustomerAddressNotFoundException;
import com.project.inventory.permission.model.Account;
import com.project.inventory.permission.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "customerAddressServiceImpl")
public class CustomerAddressServiceImpl implements CustomerAddressService {
    Logger logger = LoggerFactory.getLogger(CustomerAddressServiceImpl.class);

    @Autowired
    private CustomerAddressRepository customerAddressRepository;
    @Autowired
    private AccountService accountService;

    @Override
    public CustomerAddress saveCustomerAddress(CustomerAddress customerAddress) {
        Account account = accountService.getAccountById(1);
        logger.info("{}",
                customerAddress
                        .getFirstName()
                        .concat(" "+customerAddress
                                .getLastName())+ "Customer Address Saved Successfully");
        customerAddress.setAccount(account);
        return customerAddressRepository.save(customerAddress);
    }

    @Override
    public List<CustomerAddress> getCustomerAddresses() {
        return customerAddressRepository.findAllCustomerAddresses();
    }

    @Override
    public CustomerAddress getCustomerAddress(int id) {
        return customerAddressRepository.findById(id)
                .orElseThrow(() -> new CustomerAddressNotFoundException("Customer Address Not Found with ID: " + id));
    }

    @Override
    public CustomerAddress updateCustomerAddress(int id, CustomerAddress customerAddress) {
        CustomerAddress savedCustomerAddress = getCustomerAddress(id);

        savedCustomerAddress.setFirstName(customerAddress.getFirstName());
        savedCustomerAddress.setLastName(customerAddress.getLastName());
        savedCustomerAddress.setPhoneNumber(customerAddress.getPhoneNumber());
        savedCustomerAddress.setPostalCode(customerAddress.getPostalCode());
        savedCustomerAddress.setRegion(customerAddress.getRegion());
        savedCustomerAddress.setCity(customerAddress.getCity());
        savedCustomerAddress.setProvince(customerAddress.getProvince());
        savedCustomerAddress.setBarangay(customerAddress.getBarangay());
        savedCustomerAddress.setStreet(customerAddress.getStreet());

        return customerAddressRepository.save(savedCustomerAddress);
    }

    @Override
    public void deleteCustomerAddress(int id) {
        customerAddressRepository.deleteById(id);
    }

    @Override
    public CustomerAddress getCustomerAddressByAccountId(int accountId) {
        return customerAddressRepository.findByAccountId(accountId);
    }
}
