package com.project.inventory.store.employee.service.impl;

import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.role.model.RoleDto;
import com.project.inventory.common.permission.service.AccountService;
import com.project.inventory.common.user.model.User;
import com.project.inventory.common.user.model.UserDto;
import com.project.inventory.common.user.service.UserService;
import com.project.inventory.store.employee.model.EmployeeAccountRequest;
import com.project.inventory.store.employee.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    Logger logger = LoggerFactory.getLogger( EmployeeServiceImpl.class );
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @Override
    public void saveEmployeeAccount( EmployeeAccountRequest employeeAccountRequest ) {
        Account account = accountService.saveEmployeeAccount( employeeAccountRequest.getUsername(),
                employeeAccountRequest.getPassword(),
                employeeAccountRequest.getEmail() );
        User user = new User();
        user.setFirstName( employeeAccountRequest.getFirstName() );
        user.setLastName( employeeAccountRequest.getLastName() );
        user.setPhoneNumber( employeeAccountRequest.getPhoneNumber()  );

        userService.saveUserInformation( account, user );
    }

    @Override
    public List<UserDto> getEmployeeAccount() {
        List<UserDto> users = new ArrayList<>();
        for(UserDto user: userService.getUser()){
            if(user.getAccount().isEnabled() && user.getAccount().isLocked()){
                for( RoleDto role : user.getAccount().getRoles() ){
                    if(role.getRoleName().equals( "ADMIN" )){
                        users.add( user );
                    }
                }
            }
        }
        return users;
    }

    @Override
    public void deleteEmployeeAccount( int id ) {
        accountService.deleteAccount( id );
    }
}
