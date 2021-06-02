package com.project.inventory.product.service.impl;

import com.project.inventory.exception.product.ProductNotFound;
import com.project.inventory.exception.product.ProductNotUpdatedException;
import com.project.inventory.product.model.Product;
import com.project.inventory.product.repository.ProductRepository;
import com.project.inventory.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "productServiceImpl")
public class ProductServiceImpl implements ProductService {
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        if(product != null){
            logger.info("{}", "Product with ID "+product.getId() +" is Saved Successfully");
            return productRepository.save(product);
        }else {
            logger.info("{}", "Failed to Saved the product with ID "+product.getId());
        }
        return null;
    }

    @Override
    public Product update(int id, Product updateProduct) {
        Product product = findById(id);

        product.setName(updateProduct.getName());
        product.setDescription(updateProduct.getDescription());
        product.setPrice(updateProduct.getPrice());

        try{
           return productRepository.save(product);
        }catch (ProductNotUpdatedException productNotUpdatedException){
            throw new ProductNotUpdatedException("Product with Id " + updateProduct.getId() + " Failed to Update" );
        }

    }

    @Override
    public void delete(int id) {
        Product product = findById(id);
        if(product == null){
            throw new ProductNotFound("Product not Found");
        }
        product.setDeleted(true);
        productRepository.save(product);
        logger.info("Product with ID "+product.getId()+" Deleted Successfully");
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id);
    }
}
