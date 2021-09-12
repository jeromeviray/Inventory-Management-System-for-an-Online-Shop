package com.project.inventory.store.employee.service;

import com.project.inventory.common.user.model.UserDto;
import com.project.inventory.store.employee.model.EmployeeAccountRequest;

import java.util.List;

public interface EmployeeService {

    void saveEmployeeAccount( EmployeeAccountRequest employeeAccountRequest );

    List<UserDto> getEmployeeAccount();

    void deleteEmployeeAccount(int id);
}