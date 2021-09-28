package com.project.inventory.store.product.service;

import com.project.inventory.store.product.model.FileImage;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.model.ProductDto;
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
                           String[] removeImages);

    void deleteProduct( int id );

    List<Product> getAllAvailableProducts();

    List<Product> getProducts();

    Product getProductById( int id );

    Product getAvailableProductById( int id );

    ProductDto convertEntityToDto( Product product );

    Product convertDtoToEntity( ProductDto productDto );

    int getTotalStocks( Product product );

    byte[] getImage( String image ) throws IOException;
}
