package com.project.inventory.common.user.service;

import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.user.model.RequestUserAccount;
import com.project.inventory.common.user.model.User;
import com.project.inventory.common.user.model.UserAccount;
import com.project.inventory.common.user.model.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    void createUserAccount( UserAccount userAccount );

    void saveUserInformation( Account account, User user );

    void saveUser( User user );

    void updateUserInformation( int id, RequestUserAccount userAccount );

    void deleteUserAccount( int id );

    List<UserDto> getUsers();

    Page<UserDto> getUsersByCustomerRole( String query, Pageable pageable );

    Page<UserDto> getUsersByAdminOrSuperRole( String query, Pageable pageable );

    User getUserInformationById( int id );

    User getUserInformationByAccountId( int accountId );

    UserDto convertEntityToDto( User user );

    User convertDtoTOEntity( UserDto userDto );

}

