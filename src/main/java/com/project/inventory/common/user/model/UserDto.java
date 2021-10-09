package com.project.inventory.common.user.model;

import com.project.inventory.common.permission.model.AccountInfoDto;

import java.util.Date;

public class UserDto {

    private int id;
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private String birthday;
    private AccountInfoDto account;
//    private Collection<CustomerAddressDto> customerAddresses;

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday( String birthday ) {
        this.birthday = birthday;
    }

    public AccountInfoDto getAccount() {
        return account;
    }

    public void setAccount(AccountInfoDto account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
