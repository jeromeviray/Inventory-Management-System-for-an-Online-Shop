package com.project.inventory.store.product.service;

import com.project.inventory.store.product.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Product saveProduct( MultipartFile[] files, Product product, String store );

    Product updateProduct( int id, Product product );

    void deleteProduct( int id );

    List<Product> getAllAvailableProducts();

    List<Product> getProducts();

    Product getProductById( int id );

    Product getAvailableProductById( int id );
}
