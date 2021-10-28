package com.project.inventory.common.user.model;

import com.project.inventory.common.permission.model.AccountInfoDto;

import java.util.Date;

public class UserDto {

    private int id;
    private String firstName;
    private String lastName;
    private String birthday;
    private AccountInfoDto account;
    private String profileImage;

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

    public String getProfileImage() {   
        return profileImage;
    }

    public void setProfileImage( String profileImage ) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
