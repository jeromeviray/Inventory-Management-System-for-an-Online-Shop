package com.project.inventory.store.product.category.controller;

import com.project.inventory.store.product.category.model.CategoriesWithTotalProductsDto;
import com.project.inventory.store.product.category.model.Category;
import com.project.inventory.store.product.category.service.CategoryService;
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
    public ResponseEntity<?> getCategories( @RequestParam(value = "query", defaultValue = "") String query,
                                            @RequestParam(value = "page", defaultValue = "0") Integer page,
                                            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of( page, limit );

        Page<CategoriesWithTotalProductsDto> categories = categoryService.getCategoriesWithTotalProducts( query, pageable );

        response.put("data", categories.getContent());
        response.put("currentPage", categories.getNumber());
        response.put("totalItems", categories.getTotalElements());
        response.put("totalPages", categories.getTotalPages());

        return new ResponseEntity( response, HttpStatus.OK );
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<?> getCategoriesList(){
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK );
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
