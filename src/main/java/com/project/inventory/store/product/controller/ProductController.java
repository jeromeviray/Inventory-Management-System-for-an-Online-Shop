package com.project.inventory.store.product.controller;

import com.project.inventory.store.inventory.model.InventoryDto;
import com.project.inventory.store.inventory.service.InventoryService;
import com.project.inventory.store.product.brand.service.BrandService;
import com.project.inventory.store.product.category.service.CategoryService;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.model.ProductAndInventoryDto;
import com.project.inventory.store.product.model.ProductDto;
import com.project.inventory.store.product.service.ProductService;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( value = "api/v1/products" )
public class ProductController {
    Logger logger = LoggerFactory.getLogger( ProductController.class );

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private ModelMapper mapper;

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/save", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } )
    public ResponseEntity<?> saveProduct( @RequestPart( value = "productImages[]", required = false ) MultipartFile[] productImages,
                                          @RequestParam( "productName" ) String productName,
                                          @RequestParam( "productPrice" ) double productPrice,
                                          @RequestParam( "barcode" ) int barcode,
                                          @RequestParam( "productDescription" ) Object productDescription,
                                          @RequestParam( "brandName" ) String brandName,
                                          @RequestParam( "categoryName" ) String categoryName ) {

        productService.saveProduct(
                productImages,
                productName,
                productPrice,
                barcode,
                productDescription,
                brandName,
                categoryName );
        return new ResponseEntity( HttpStatus.OK );

    }

    @RequestMapping( value = "/getImages/bytesArrays/{path}/{image}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE )
    public ResponseEntity<?> getImage( @PathVariable String path,
                                       @PathVariable String image ) throws IOException {

        InputStream readImage = servletContext.getResourceAsStream( "WEB-INF/inventory-management-system-reactjs/public/images/products/" + path + "/" + image );

        return new ResponseEntity( IOUtils.toByteArray( readImage ), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/update/{id}", method = RequestMethod.PUT )
    public ResponseEntity<?> updateProduct( @PathVariable int id,
                                            @RequestPart( value = "productImages[]", required = false ) MultipartFile[] productImages,
                                            @RequestParam( "productName" ) String productName,
                                            @RequestParam( "productPrice" ) double productPrice,
                                            @RequestParam( "barcode" ) int barcode,
                                            @RequestParam( "productDescription" ) Object productDescription,
                                            @RequestParam( "brandName" ) String brandName,
                                            @RequestParam( "categoryName" ) String categoryName,
                                            @RequestParam( value = "removedImages[]", required = false ) String[] removeImages ) throws Exception {
        return ResponseEntity.ok( productService
                .updateProduct( id,
                        productImages,
                        productName,
                        productPrice,
                        barcode,
                        productDescription,
                        brandName,
                        categoryName,
                        removeImages ) );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') OR hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = "/delete/{id}", method = RequestMethod.DELETE )
    public void deleteProduct( @PathVariable int id ) {
        productService.deleteProduct( id );
    }

    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN') OR hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<?> getProducts( @RequestParam( value = "query", defaultValue = "", required = false ) String query,
                                          @RequestParam( value = "page", defaultValue = "0" ) Integer page,
                                          @RequestParam( value = "limit", defaultValue = "0" ) Integer limit ) throws ParseException {

        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of( page, limit );
        Page<ProductAndInventoryDto> products = productService.getProducts( query, pageable );

        response.put( "data", products.getContent() );
        response.put( "currentPage", products.getNumber() );
        response.put( "totalItems", products.getTotalElements() );
        response.put( "totalPages", products.getTotalPages() );

        return new ResponseEntity( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public ResponseEntity<?> getProductById( @PathVariable int id ) throws IOException {

        return new ResponseEntity( productService.getProductAndInventoryByProductId( id ), HttpStatus.OK );
    }

    @RequestMapping( value = "/details/{id}", method = RequestMethod.GET )
    public ResponseEntity<?> getProductSummaryDetails( @PathVariable int id ) throws IOException {

        return ResponseEntity.ok( productService.getProductAndInventoryByProductId( id ) );
    }

    @RequestMapping( value = "/discover", method = RequestMethod.GET )
    public ResponseEntity<?> getDiscoverProducts( @RequestParam( value = "query", defaultValue = "", required = false ) String query,
                                                  @RequestParam( value = "page", defaultValue = "0" ) Integer page,
                                                  @RequestParam( value = "limit", defaultValue = "0" ) Integer limit ) throws IOException, ParseException {
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of( page, limit );
        Page<ProductAndInventoryDto> products = productService.getProducts( query, pageable );

        response.put( "data", products.getContent() );
        response.put( "currentPage", products.getNumber() );
        response.put( "totalItems", products.getTotalElements() );
        response.put( "totalPages", products.getTotalPages() );

        return ResponseEntity.ok( response );
    }

    @RequestMapping( value = "/search", method = RequestMethod.GET )
    public ResponseEntity<?> searchProductByBarcodeOrProductName( @RequestParam( value = "query", defaultValue = "", required = false ) String query,
                                                                  @RequestParam( value = "page", defaultValue = "0" ) Integer page,
                                                                  @RequestParam( value = "limit", defaultValue = "0" ) Integer limit ) throws ParseException {
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of( page, limit );
        Page<ProductAndInventoryDto> products = productService.getProducts( query, pageable );
        List<ProductDto> productDto = new ArrayList<>();
        for ( ProductAndInventoryDto productAndInventoryDto : products.getContent() ) {
            productDto.add( productAndInventoryDto.getProduct() );
        }
        response.put( "data", productDto );
        response.put( "currentPage", products.getNumber() );
        response.put( "totalItems", products.getTotalElements() );
        response.put( "totalPages", products.getTotalPages() );

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/category/{categoryName}", method = RequestMethod.GET )
    public ResponseEntity<?> getProductByCategoryName( @PathVariable String categoryName,
                                                       @RequestParam( value = "query", defaultValue = "", required = false ) String query,
                                                       @RequestParam( value = "page", defaultValue = "0" ) Integer page,
                                                       @RequestParam( value = "limit", defaultValue = "10" ) Integer limit ) throws ParseException {

        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of( page, limit );
        Page<ProductAndInventoryDto> products = productService.getProductByCategoryName( categoryName, query, pageable );
        List<ProductAndInventoryDto> productDto = new ArrayList<>();
        for ( ProductAndInventoryDto productAndInventoryDto : products.getContent() ) {
            productDto.add( productAndInventoryDto );
        }
        response.put( "data", productDto );
        response.put( "currentPage", products.getNumber() );
        response.put( "totalItems", products.getTotalElements() );
        response.put( "totalPages", products.getTotalPages() );

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ResponseEntity<?> getProductsByStatus( @RequestParam( value = "status", defaultValue = "", required = false ) String status,
                                                 @RequestParam( value = "query", defaultValue = "", required = false ) String query,
                                                 @RequestParam( value = "page", defaultValue = "0" ) Integer page,
                                                 @RequestParam( value = "limit", defaultValue = "10" ) Integer limit ) {
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of( page, limit );
        Page<ProductAndInventoryDto> products = productService.getProductByStatus( query, status, pageable );
        List<ProductAndInventoryDto> productDto = new ArrayList<>();
        for ( ProductAndInventoryDto productAndInventoryDto : products.getContent() ) {
            productDto.add( productAndInventoryDto );
        }
        response.put( "data", productDto );
        response.put( "currentPage", products.getNumber() );
        response.put( "totalItems", products.getTotalElements() );
        response.put( "totalPages", products.getTotalPages() );

        return new ResponseEntity<>( response, HttpStatus.OK );
    }
    @RequestMapping(value = "/promo", method = RequestMethod.GET)
    public ResponseEntity<?> getProductsWithPromo( @RequestParam( value = "status", defaultValue = "", required = false ) String status,
                                                 @RequestParam( value = "query", defaultValue = "", required = false ) String query,
                                                 @RequestParam( value = "page", defaultValue = "0" ) Integer page,
                                                 @RequestParam( value = "limit", defaultValue = "10" ) Integer limit ) throws ParseException {
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of( page, limit );
        Page<ProductAndInventoryDto> products = productService.getProductsWithPromo( query, status, pageable );
        List<ProductAndInventoryDto> productDto = new ArrayList<>();
        for ( ProductAndInventoryDto productAndInventoryDto : products.getContent() ) {
            productDto.add( productAndInventoryDto );
        }
        response.put( "data", productDto );
        response.put( "currentPage", products.getNumber() );
        response.put( "totalItems", products.getTotalElements() );
        response.put( "totalPages", products.getTotalPages() );

        return new ResponseEntity<>( response, HttpStatus.OK );
    }
}
