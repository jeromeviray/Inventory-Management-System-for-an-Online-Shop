package com.project.inventory.user.information.service;

import com.project.inventory.permission.model.Account;
import com.project.inventory.user.information.model.UserInformation;
import com.project.inventory.user.information.model.UserInformationDto;

public interface UserInformationService {
    void saveUserInformation(Account account, UserInformation userInformation);
    void updateUserInformation(int id, UserInformation userInformation);
    void deleteUserInformation(int id);
    UserInformation getUserInformationById(int id);
    UserInformation getUserInformationByAccountId(int accountId);

    UserInformationDto convertEntityToDto(UserInformation userInformation);
    UserInformation convertDtoTOEntity(UserInformationDto userInformationDto);

}
