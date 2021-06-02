package com.project.inventory.product.service.impl;

import com.project.inventory.product.model.Product;
import com.project.inventory.product.repository.ProductRepository;
import com.project.inventory.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    @Autowired
    private ProductRepository productRepository;

    @Override
    public void save(Product product) {
        if(product != null){
            productRepository.save(product);
            logger.info("{}", "Product with ID "+product.getId() +" is Saved Successfully");
        }else {
            logger.info("{}", "Failed to Saved the product with ID "+product.getId());
        }

    }

    @Override
    public Product update(int id) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Product findById(int id) {
        return null;
    }
}
