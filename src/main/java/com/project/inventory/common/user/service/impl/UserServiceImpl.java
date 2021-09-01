package com.project.inventory.common.user.service.impl;

import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.service.AccountService;
import com.project.inventory.common.user.model.User;
import com.project.inventory.common.user.model.UserDto;
import com.project.inventory.common.user.repository.UserRepository;
import com.project.inventory.common.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "userInformationServiceImpl")
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public void saveUserInformation(Account savedAccount, User user) {
        Account account = accountService.getAccountById(savedAccount.getId());
        user.setAccount(account);
        userRepository.save(user);
    }

    @Override
    public void updateUserInformation(int id, User user) {
        User updateSavedUser = getUserInformationById(id);

        updateSavedUser.setFirstName(user.getFirstName());
        updateSavedUser.setLastName(user.getLastName());
        updateSavedUser.setPhoneNumber(user.getPhoneNumber());
        updateSavedUser.setBirthDay(user.getBirthDay());

        userRepository.save(updateSavedUser);
    }

    @Override
    public void deleteUserInformation(int id) {
        User user = getUserInformationById(id);
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getUser() {
        List<UserDto> users = new ArrayList<>();
        for(User user : userRepository.findAll()){
            users.add( convertEntityToDto( user ) );
        }
        return users;
    }

    @Override
    public User getUserInformationById(int id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public User getUserInformationByAccountId(int accountId) {
        return userRepository.findByAccountId(accountId)
                .orElseThrow();
    }

    @Override
    public UserDto convertEntityToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User convertDtoTOEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
