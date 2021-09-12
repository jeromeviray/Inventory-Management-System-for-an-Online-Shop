package com.project.inventory.store.product.controller;

import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.model.ProductDto;
import com.project.inventory.store.product.service.ProductService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping( value = "api/v1/products" )
public class ProductController {
    Logger logger = LoggerFactory.getLogger( ProductController.class );

    @Autowired
    private ProductService productService;

    @Autowired
    private ServletContext servletContext;

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
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

    @RequestMapping( value = "/getImages/bytesArrays/{image}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE )
    public ResponseEntity<?> getImage( @PathVariable String image, HttpServletResponse response ) throws IOException {
        InputStream readImage = servletContext.getResourceAsStream( "WEB-INF/inventory-management-system-reactjs/public/images/products/" + image );
        return new ResponseEntity( IOUtils.toByteArray( readImage ), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/update/{id}", method = RequestMethod.PUT )
    public ResponseEntity<?> updateProduct( @PathVariable int id,
                                            @RequestBody Product product ) {
        logger.info( "{}", product );
        return ResponseEntity.ok( productService.updateProduct( id, product ) );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/delete/{id}", method = RequestMethod.POST )
    public void deleteProduct( @PathVariable int id ) {
        productService.deleteProduct( id );
    }

    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<?> getAllAvailableProducts() {
        List<ProductDto> products = new ArrayList<>();
        for ( Product product : productService.getAllAvailableProducts() ) {
            products.add( productService.convertEntityToDto( product ) );
        }
        return new ResponseEntity( products, HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/all", method = RequestMethod.GET )
    public List<Product> getAllProducts() {
        return productService.getProducts();
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public ResponseEntity<?> getProductById( @PathVariable int id ) throws IOException {
        Product product = productService.getProductById( id );

        return ResponseEntity.ok( productService.convertEntityToDto( product ) );
    }
    @RequestMapping( value = "/details/{id}", method = RequestMethod.GET )
    public ResponseEntity<?> getProductSummaryDetails( @PathVariable int id ) throws IOException {
        Product product = productService.getProductById( id );

        return ResponseEntity.ok( productService.convertEntityToDto( product ) );
    }

    @RequestMapping( value = "/discover", method = RequestMethod.GET )
    public ResponseEntity<?> getDiscoverProducts() throws IOException {
        List<ProductDto> products = new ArrayList<>();
        for ( Product product : productService.getAllAvailableProducts() ) {
            products.add( productService.convertEntityToDto( product ) );
        }
        return ResponseEntity.ok( products );
    }
}
