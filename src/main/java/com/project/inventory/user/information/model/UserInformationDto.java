package com.project.inventory.user.information.model;

import com.project.inventory.customer.address.model.CustomerAddressDto;
import com.project.inventory.user.permission.model.AccountInfoDto;

import java.util.Collection;
import java.util.Date;

public class UserInformationDto {

    private int id;
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private Date birthDay;
    private AccountInfoDto account;
    private Collection<CustomerAddressDto> customerAddresses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public AccountInfoDto getAccount() {
        return account;
    }

    public void setAccount(AccountInfoDto account) {
        this.account = account;
    }

    public Collection<CustomerAddressDto> getCustomerAddresses() {
        return customerAddresses;
    }

    public void setCustomerAddresses(Collection<CustomerAddressDto> customerAddresses) {
        this.customerAddresses = customerAddresses;
    }

    @Override
    public String toString() {
        return "UserInformationDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", birthDay=" + birthDay +
                ", account=" + account +
                ", customerAddresses=" + customerAddresses +
                '}';
    }
}
