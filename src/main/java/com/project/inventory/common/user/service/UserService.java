package com.project.inventory.common.user.service;

import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.common.user.model.User;
import com.project.inventory.common.user.model.UserDto;

public interface UserService {
    void saveUserInformation(Account account, User user);
    void updateUserInformation(int id, User user);
    void deleteUserInformation(int id);
    User getUserInformationById(int id);
    User getUserInformationByAccountId(int accountId);

    UserDto convertEntityToDto(User user);
    User convertDtoTOEntity(UserDto userDto);

}
