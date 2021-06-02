package com.project.inventory.product.controller;

import com.project.inventory.product.model.Product;
import com.project.inventory.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/product")
public class ProductRestController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Product> save(@RequestBody Product product){
        return ResponseEntity.ok(productService.save(product));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> update(@PathVariable int id,
                                    Product product){
        return ResponseEntity.ok(productService.update(id, product));
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void delete(@PathVariable int id){
        productService.delete(id);
    }

    @RequestMapping(value = "find/all/products", method = RequestMethod.GET)
    public List<Product> findAllAvailableProducts(){
        return productService.findAll();
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> findProductById(@PathVariable int id){
        return ResponseEntity.ok(productService.findById(id));
    }
}
