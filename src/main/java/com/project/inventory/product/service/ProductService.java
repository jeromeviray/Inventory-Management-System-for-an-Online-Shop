package com.project.inventory.product.service;

import com.project.inventory.product.model.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    Product updateProduct(int id, Product product);
    void deleteProduct(int id);
    List<Product> getAllAvailableProducts();
    List<Product> getProducts();
    Product getProductById(int id);
    Product getAvailableProductById(int id);
}
