package com.project.inventory.common.user.service;

import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.user.model.User;
import com.project.inventory.common.user.model.UserAccount;
import com.project.inventory.common.user.model.UserDto;

import java.util.List;

public interface UserService {

    void createUserAccount( UserAccount userAccount );

    void saveUserInformation( Account account, User user );

    void updateUserInformation( int id, User user );

    void deleteUserAccount(int id);

    List<UserDto> getUsers();

    List<UserDto> getUsersByCustomerRole();

    List<UserDto> getUsersByRole();

    User getUserInformationById( int id );

    User getUserInformationByAccountId( int accountId );

    UserDto convertEntityToDto( User user );

    User convertDtoTOEntity( UserDto userDto );

}

