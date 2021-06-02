package com.project.inventory.product.service;

import com.project.inventory.product.model.Product;

import java.util.List;

public interface ProductService {
    Product save(Product product);
    Product update(int id, Product product);
    void delete(int id);
    List<Product> findAll();
    Product findById(int id);

}
