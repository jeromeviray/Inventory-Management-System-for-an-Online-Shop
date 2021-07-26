package com.project.inventory.user.information.service.impl;

import com.project.inventory.user.permission.model.Account;
import com.project.inventory.user.permission.service.AccountService;
import com.project.inventory.user.information.model.UserInformation;
import com.project.inventory.user.information.model.UserInformationDto;
import com.project.inventory.user.information.repository.UserInformationRepository;
import com.project.inventory.user.information.service.UserInformationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userInformationServiceImpl")
public class UserInformationServiceImpl implements UserInformationService {
    Logger logger = LoggerFactory.getLogger(UserInformationServiceImpl.class);
    @Autowired
    private UserInformationRepository userInformationRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public void saveUserInformation(Account savedAccount, UserInformation userInformation) {
        Account account = accountService.getAccountById(savedAccount.getId());
        userInformation.setAccount(account);
        userInformationRepository.save(userInformation);
    }

    @Override
    public void updateUserInformation(int id, UserInformation userInformation) {
        UserInformation updateSavedUserInformation = getUserInformationById(id);

        updateSavedUserInformation.setFirstName(userInformation.getFirstName());
        updateSavedUserInformation.setLastName(userInformation.getLastName());
        updateSavedUserInformation.setPhoneNumber(userInformation.getPhoneNumber());
        updateSavedUserInformation.setBirthDay(userInformation.getBirthDay());

        userInformationRepository.save(updateSavedUserInformation);
    }

    @Override
    public void deleteUserInformation(int id) {
        UserInformation userInformation = getUserInformationById(id);
        userInformationRepository.delete(userInformation);
    }

    @Override
    public UserInformation getUserInformationById(int id) {
        return userInformationRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public UserInformation getUserInformationByAccountId(int accountId) {
        return userInformationRepository.findByAccountId(accountId)
                .orElseThrow();
    }

    @Override
    public UserInformationDto convertEntityToDto(UserInformation userInformation) {
        return modelMapper.map(userInformation, UserInformationDto.class);
    }

    @Override
    public UserInformation convertDtoTOEntity(UserInformationDto userInformationDto) {
        return modelMapper.map(userInformationDto, UserInformation.class);
    }
}
