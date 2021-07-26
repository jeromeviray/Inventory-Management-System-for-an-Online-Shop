package com.project.inventory.common.user.controller;

import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.common.persmision.service.AccountService;
import com.project.inventory.common.persmision.service.AuthenticatedUser;
import com.project.inventory.common.user.model.User;
import com.project.inventory.common.user.model.UserDto;
import com.project.inventory.common.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/account/information")
public class UserInformationController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticatedUser authenticatedUser;
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveUserInformation(@RequestBody User user){
        Account account = accountService.getAccountByUsername(authenticatedUser
                                                                .getUserDetails()
                                                                .getUsername());
        userService.saveUserInformation(account, user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserInformationById(@PathVariable int id){
        UserDto userInformation = userService.convertEntityToDto(
                userService.getUserInformationById(id));
        return new ResponseEntity(userInformation, HttpStatus.OK);
    }

    @RequestMapping(value = "/authenticated", method = RequestMethod.GET)
    public ResponseEntity<?> getUserInformationByAccountId(){
        UserDto userInformation = userService.convertEntityToDto(
                userService.getUserInformationByAccountId(
                        authenticatedUser.getUserDetails().getId())
        );
        return new ResponseEntity(userInformation,
                HttpStatus.OK);
    }
}
