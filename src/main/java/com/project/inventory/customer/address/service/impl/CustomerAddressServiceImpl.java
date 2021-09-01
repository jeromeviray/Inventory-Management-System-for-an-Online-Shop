package com.project.inventory.customer.address.service.impl;

import com.project.inventory.common.permission.service.AuthenticatedUser;
import com.project.inventory.customer.address.model.CustomerAddress;
import com.project.inventory.customer.address.model.CustomerAddressDto;
import com.project.inventory.customer.address.repository.CustomerAddressRepository;
import com.project.inventory.customer.address.service.CustomerAddressService;
import com.project.inventory.exception.notFound.customer.CustomerAddressNotFoundException;
import com.project.inventory.common.permission.model.Account;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "customerAddressServiceImpl")
public class CustomerAddressServiceImpl implements CustomerAddressService {
    Logger logger = LoggerFactory.getLogger(CustomerAddressServiceImpl.class);

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Override
    public CustomerAddress saveCustomerAddress(CustomerAddress customerAddress) {
        Account account = authenticatedUser.getUserDetails();
        logger.info(String.format("{}",
                customerAddress
                        .getFirstName().concat(" "+customerAddress
                        .getLastName())+ "Customer Address Saved Successfully"));

        customerAddress.setAccount(account);
        return customerAddressRepository.save(customerAddress);
    }

    @Override
    public List<CustomerAddressDto> getCustomerAddresses() {
        List<CustomerAddressDto> customerAddressesDto = new ArrayList<>();
        for (CustomerAddress customerAddress : customerAddressRepository.findAllCustomerAddresses()){
            customerAddressesDto.add(convertEntityToDto(customerAddress));
        }
        return customerAddressesDto;
    }

    @Override
    public CustomerAddressDto getCustomerAddress(int id) {
        CustomerAddress customerAddress = customerAddressRepository.findById(id)
                .orElseThrow(() -> new CustomerAddressNotFoundException(String.format("Customer Address Not Found with ID: " + id)));
        return convertEntityToDto(customerAddress);
    }

    @Override
    public CustomerAddress updateCustomerAddress(int id, CustomerAddress customerAddress) {
        CustomerAddress savedCustomerAddress = customerAddressRepository.findByAccountId(id);

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
    public CustomerAddressDto getCustomerAddressByAccountId(int accountId) {
        return convertEntityToDto(customerAddressRepository.findByAccountId(accountId));
    }

    // converting entity to dto
    @Override
    public CustomerAddressDto convertEntityToDto(CustomerAddress customerAddress){
        return mapper.map(customerAddress, CustomerAddressDto.class);
    }
    // converting dto to entity
    @Override
    public CustomerAddress convertDtoToEntity(CustomerAddressDto customerAddressDto){
        return mapper.map(customerAddressDto, CustomerAddress.class);
    }
}
