package com.project.inventory.common.user.controller;

import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.service.AccountService;
import com.project.inventory.common.permission.service.AuthenticatedUser;
import com.project.inventory.common.user.model.RequestUserAccount;
import com.project.inventory.common.user.model.User;
import com.project.inventory.common.user.model.UserAccount;
import com.project.inventory.common.user.model.UserDto;
import com.project.inventory.common.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/users/account")
public class UserController {

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
    @RequestMapping(value ="/create", method = RequestMethod.POST)
    public ResponseEntity<?> createUserAccount( @RequestBody UserAccount userAccount ){
        userService.createUserAccount( userAccount );
        return new ResponseEntity(HttpStatus.OK);
    }
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public ResponseEntity<?> getUser(){
//        return new ResponseEntity(userService.getUser(),HttpStatus.OK);
//    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserInformationById(@PathVariable int id){
        UserDto userInformation = userService.convertEntityToDto(
                userService.getUserInformationById(id));
        return new ResponseEntity(userInformation, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserAccount( @PathVariable int id, @RequestBody RequestUserAccount userAccount ){
        userService.updateUserInformation( id, userAccount );
        return new ResponseEntity( HttpStatus.OK);
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

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerAccounts( @RequestParam(value = "query", defaultValue = "") String query,
                                                  @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit ){
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of( page, limit );
        Page<UserDto> users = userService.getUsersByCustomerRole(query, pageable);

        response.put("data", users.getContent());
        response.put("currentPage", users.getNumber());
        response.put("totalItems", users.getTotalElements());
        response.put("totalPages", users.getTotalPages());
        return new ResponseEntity(response , HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getUsersByAdminAndSuperRole( @RequestParam(value = "query", defaultValue = "") String query,
                                                          @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                          @RequestParam(value = "limit", defaultValue = "10") Integer limit ){
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of( page, limit );
        Page<UserDto> users = userService.getUsersByAdminOrSuperRole(query, pageable);

        response.put("data", users.getContent());
        response.put("currentPage", users.getNumber());
        response.put("totalItems", users.getTotalElements());
        response.put("totalPages", users.getTotalPages());

        return new ResponseEntity( response, HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEmployeeAccount( @PathVariable int id ){
        userService.deleteUserAccount(id);
        return new ResponseEntity( HttpStatus.OK );
    }
}
