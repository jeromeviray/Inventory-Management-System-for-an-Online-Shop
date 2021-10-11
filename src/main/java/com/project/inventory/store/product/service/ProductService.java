package com.project.inventory.store.product.service;

import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.model.ProductAndInventoryDto;
import com.project.inventory.store.product.model.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    Page<ProductAndInventoryDto> getProducts( String query, Pageable pageable );

    ProductAndInventoryDto getProductAndInventoryByProductId( int id );

    Product getProductById( int id );

    Product getAvailableProductById( int id );

    ProductDto convertEntityToDto( Product product );

    Product convertDtoToEntity( ProductDto productDto );

    byte[] getImage( String image ) throws IOException;

    Page<ProductAndInventoryDto> getProductByCategoryName( String categoryName, String query, Pageable pageable );

    Page<ProductAndInventoryDto> getProductByStatus(String query, String status, Pageable pageable);

}
