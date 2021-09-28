package com.project.inventory.store.product.controller;

import com.project.inventory.store.product.brand.service.BrandService;
import com.project.inventory.store.product.category.service.CategoryService;
import com.project.inventory.store.product.model.GetInventory;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.model.ProductDto;
import com.project.inventory.store.product.service.ProductService;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( value = "api/v1/products" )
public class ProductController {
    Logger logger = LoggerFactory.getLogger( ProductController.class );

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
    @RequestMapping( value = "/save", method = RequestMethod.POST )
    public ResponseEntity<?> saveProduct( @RequestPart( "productImages[]" ) MultipartFile[] productImages,
                                          @RequestParam( "productName" ) String productName,
                                          @RequestParam( "productPrice" ) double productPrice,
                                          @RequestParam( "barcode" ) int barcode,
                                          @RequestParam( "productDescription" ) Object productDescription,
                                          @RequestParam( "brandName" ) String brandName,
                                          @RequestParam( "categoryName" ) String categoryName ) {

        Product product = new Product();
        product.setName( productName );
        product.setPrice( productPrice );
        product.setDescription( ( String ) productDescription );
        product.setBarcode( barcode );
        product.setBrand( brandService.getBrandByBrandName( brandName ) );
        product.setCategory( categoryService.getCategoryByCategoryName( categoryName ) );
        productService.saveProduct( productImages, product );
        return new ResponseEntity( HttpStatus.OK );

    }

    @RequestMapping( value = "/getImages/bytesArrays/{path}/{image}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE )
    public ResponseEntity<?> getImage( @PathVariable String path,
                                       @PathVariable String image ) throws IOException {

        InputStream readImage = path.equals( "null" ) ? readImage = servletContext.getResourceAsStream( "WEB-INF/inventory-management-system-reactjs/public/images/products/"+ image )
                : servletContext.getResourceAsStream( "WEB-INF/inventory-management-system-reactjs/public/images/products/" + path +"/" + image );

        return new ResponseEntity( IOUtils.toByteArray( readImage ), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/update/{id}", method = RequestMethod.PUT )
    public ResponseEntity<?> updateProduct( @PathVariable int id,
                                            @RequestPart( "productImages[]" ) MultipartFile[] productImages,
                                            @RequestParam( "productName" ) String productName,
                                            @RequestParam( "productPrice" ) double productPrice,
                                            @RequestParam( "barcode" ) int barcode,
                                            @RequestParam( "productDescription" ) Object productDescription,
                                            @RequestParam( "brandName" ) String brandName,
                                            @RequestParam( "categoryName" ) String categoryName,
                                            @RequestParam( "removeImages[]" ) String[] removeImages ) {

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

    //    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public ResponseEntity<?> getProductById( @PathVariable int id ) throws IOException {
        Product product = productService.getProductById( id );

        GetInventory inventory = new GetInventory();

        inventory.setTotalStock( productService.getTotalStocks( product ) );
        inventory.setThreshold( product.getInventory().getThreshold() );
        inventory.setStatus( product.getInventory().getStatus().name() );

        ProductDto productDto = mapper.map( product, ProductDto.class );
        productDto.setInventory( inventory );

        return ResponseEntity.ok( productDto );
//        return ResponseEntity.ok( mapper.map( product, ProductDto.class )  );
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
