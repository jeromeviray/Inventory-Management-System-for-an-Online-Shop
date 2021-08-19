package com.project.inventory.store.product.controller;

import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping( value = "api/v1/products" )
public class ProductController {
    Logger logger = LoggerFactory.getLogger( ProductController.class );

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping( value = "/save", method = RequestMethod.POST )
    public ResponseEntity<?> saveProduct( @RequestPart( "productImages[]" ) MultipartFile[] productImages,
                                          @RequestParam( "productName" ) String productName,
                                          @RequestParam( "productPrice" ) double productPrice,
                                          @RequestParam( "productDescription" ) Object productDescription,
                                          @RequestParam( "branch" ) String branch ) {
        String description = ( String ) productDescription;
        Product product = new Product();
        product.setName( productName );
        product.setPrice( productPrice );
        product.setDescription( description );
        productService.saveProduct( productImages, product, branch );
        return new ResponseEntity( HttpStatus.OK );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping( value = "/update/{id}", method = RequestMethod.PUT )
    public ResponseEntity<?> updateProduct( @PathVariable int id,
                                            @RequestBody Product product ) {
        logger.info( "{}", product );
        return ResponseEntity.ok( productService.updateProduct( id, product ) );
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping( value = "/delete/{id}", method = RequestMethod.POST )
    public void deleteProduct( @PathVariable int id ) {
        productService.deleteProduct( id );
    }

    @RequestMapping( value = "", method = RequestMethod.GET )
    public List<Product> getAllAvailableProducts() {
        return productService.getAllAvailableProducts();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping( value = "/all", method = RequestMethod.GET )
    public List<Product> getAllProducts() {
        return productService.getProducts();
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public ResponseEntity<Product> getAvailableProduct( @PathVariable int id ) {
        return ResponseEntity.ok( productService.getAvailableProductById( id ) );
    }

}
