package com.project.inventory.product.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.project.inventory.jsonView.View;
import com.project.inventory.product.model.Product;
import com.project.inventory.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/products")
public class ProductRestController {
    Logger logger = LoggerFactory.getLogger(ProductRestController.class);

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        return new ResponseEntity<Product>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProduct(@PathVariable int id,
                                    @RequestBody Product product){
        logger.info("{}", product);
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Product> getAllAvailableProducts(){
        return productService.getAllAvailableProducts();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Product> getAllProducts(){
        return productService.getProducts();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> getAvailableProduct(@PathVariable int id){
        return ResponseEntity.ok(productService.getAvailableProductById(id));
    }

}
