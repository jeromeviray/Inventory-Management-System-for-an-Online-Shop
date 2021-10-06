package com.project.inventory.store.product.service;

import com.project.inventory.store.product.model.FileImage;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.model.ProductAndInventoryDto;
import com.project.inventory.store.product.model.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
                           String[] removeImages) throws Exception;

    void deleteProduct( int id );

//    List<Product> getAllAvailableProducts();

    Page<ProductAndInventoryDto> getProducts( String query, Pageable pageable );

    ProductAndInventoryDto getProductAndInventoryByProductId(int id);
    Product getProductById( int id );

    Product getAvailableProductById( int id );

    ProductDto convertEntityToDto( Product product );

    Product convertDtoToEntity( ProductDto productDto );
//
//    int getTotalStocks( Product product );

    byte[] getImage( String image ) throws IOException;
//
//    List<Product> searchProductByBarcodeOrProductName(String query);
}
