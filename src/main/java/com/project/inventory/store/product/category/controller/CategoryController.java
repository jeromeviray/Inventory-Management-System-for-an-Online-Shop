package com.project.inventory.store.product.category.controller;

import com.project.inventory.store.product.category.model.Category;
import com.project.inventory.store.product.category.service.CategoryService;
import com.twilio.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( value = "/api/v1/categories" )
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping( value = "/save", method = RequestMethod.POST )
    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    public ResponseEntity<?> saveCategory( @RequestBody Category category ) {
        categoryService.saveCategory( category );
        return new ResponseEntity( HttpStatus.OK );
    }
    @RequestMapping( value = "/update/{id}", method = RequestMethod.PUT )
    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    public ResponseEntity<?> updateCategory(@PathVariable int id, @RequestBody Category category ) {
        return new ResponseEntity( categoryService.updateCategory( id, category ), HttpStatus.OK );
    }

    @RequestMapping( value = "", method = RequestMethod.GET )
    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    public ResponseEntity<?> getCategories() {
        return new ResponseEntity( categoryService.getCategoriesWithTotalProducts(), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    public ResponseEntity<?> getCategory( @PathVariable int id ) {
        return new ResponseEntity( categoryService.getCategory( id ), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')" )
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCategory(@PathVariable int id){
        categoryService.deleteCategory( id );
        return new ResponseEntity<>( HttpStatus.OK );
    }
}
