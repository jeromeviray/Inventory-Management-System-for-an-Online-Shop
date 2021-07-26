package com.project.inventory.user.information.controller;

import com.project.inventory.user.permission.model.Account;
import com.project.inventory.user.permission.service.AccountService;
import com.project.inventory.user.permission.service.AuthenticatedUser;
import com.project.inventory.user.information.model.UserInformation;
import com.project.inventory.user.information.model.UserInformationDto;
import com.project.inventory.user.information.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/account/information")
public class UserInformationController {

    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    private AuthenticatedUser authenticatedUser;
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveUserInformation(@RequestBody UserInformation userInformation){
        Account account = accountService.getAccountByUsername(authenticatedUser
                                                                .getUserDetails()
                                                                .getUsername());
        userInformationService.saveUserInformation(account, userInformation);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserInformationById(@PathVariable int id){
        UserInformationDto userInformation = userInformationService.convertEntityToDto(
                userInformationService.getUserInformationById(id));
        return new ResponseEntity(userInformation, HttpStatus.OK);
    }

    @RequestMapping(value = "/authenticated", method = RequestMethod.GET)
    public ResponseEntity<?> getUserInformationByAccountId(){
        UserInformationDto userInformation = userInformationService.convertEntityToDto(
                userInformationService.getUserInformationByAccountId(
                        authenticatedUser.getUserDetails().getId())
        );
        return new ResponseEntity(userInformation,
                HttpStatus.OK);
    }
}
