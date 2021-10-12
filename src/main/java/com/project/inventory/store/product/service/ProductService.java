package com.project.inventory.store.product.service;

import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.model.ProductAndInventoryDto;
import com.project.inventory.store.product.model.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

public interface ProductService {
    Product saveProduct( MultipartFile[] productImages,
                         String productName,
                         double productPrice,
                         int barcode,
                         Object productDescription,
                         String brandName,
                         String categoryName );

    Product updateProduct( int id, MultipartFile[] productImages,
                           String productName,
                           double productPrice,
                           int barcode,
                           Object productDescription,
                           String brandName,
                           String categoryName,
                           String[] removeImages ) throws Exception;

    void deleteProduct( int id );

    Page<ProductAndInventoryDto> getProducts( String query, Pageable pageable ) throws ParseException;

    ProductAndInventoryDto getProductAndInventoryByProductId( int id ) throws ParseException;

    Product getProductById( int id );

    Product getAvailableProductById( int id );

    ProductDto convertEntityToDto( Product product );

    Product convertDtoToEntity( ProductDto productDto );

    byte[] getImage( String image ) throws IOException;

    Page<ProductAndInventoryDto> getProductByCategoryName( String categoryName, String query, Pageable pageable ) throws ParseException;

    Page<ProductAndInventoryDto> getProductByStatus(String query, String status, Pageable pageable);

    Page<ProductAndInventoryDto> getProductsWithPromo(String query, String status, Pageable pageable) throws ParseException;

}
