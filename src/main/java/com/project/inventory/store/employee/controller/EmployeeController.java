package com.project.inventory.store.employee.controller;

import com.project.inventory.store.employee.model.EmployeeAccountRequest;
import com.project.inventory.store.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/account/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveEmployeeAccount( @RequestBody EmployeeAccountRequest employeeAccount ){
        employeeService.saveEmployeeAccount( employeeAccount );

        return new ResponseEntity( HttpStatus.CREATED );
    }
    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployees( ){
        return new ResponseEntity( employeeService.getEmployeeAccount(), HttpStatus.CREATED );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEmployeeAccount( @PathVariable int id ){
        employeeService.deleteEmployeeAccount(id);
        return new ResponseEntity( HttpStatus.OK );
    }
}
