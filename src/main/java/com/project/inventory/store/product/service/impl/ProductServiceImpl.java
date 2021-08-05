package com.project.inventory.store.product.service.impl;

import com.project.inventory.exception.invalid.InvalidException;
import com.project.inventory.exception.notFound.product.ProductNotFound;
import com.project.inventory.exception.serverError.product.ProductNotUpdatedException;
import com.project.inventory.store.information.model.StoreInformation;
import com.project.inventory.store.information.service.StoreInformationService;
import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.repository.InventoryRepository;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.model.ProductDto;
import com.project.inventory.store.product.repository.ProductRepository;
import com.project.inventory.store.product.service.ProductService;
import org.hibernate.service.NullServiceException;
import org.modelmapper.ModelMapper;
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
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private StoreInformationService storeInformationService;

    @Override
    public Product saveProduct(Product product) {
        if(product != null){
            try {
                StoreInformation storeInformation = storeInformationService.getStoreInformationByLocation(product.getStoreInformation().getLocation());

                product.setStoreInformation(storeInformation);
                Product savedProduct = productRepository.save(product);

                saveProductInventory(savedProduct);
                return savedProduct;

            } catch (InvalidException e) {
                throw new InvalidException("Unsuccessfully saved. Please Try Again!");
            }
        }else {
            throw new NullServiceException(super.getClass());
        }
    }
    public void saveProductInventory(Product product){
        // it will save the product in inventory when creating a product
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventoryRepository.save(inventory);
    }
    @Override
    public Product updateProduct(int id, Product updateProduct) {
        Product product = getProductById(id); // existing product in database

        product.setName(updateProduct.getName());
        product.setDescription(updateProduct.getDescription());
        product.setPrice(updateProduct.getPrice());
        product.setDeleted(updateProduct.isDeleted());

        try{
            return productRepository.save(product);
        }catch (ProductNotUpdatedException productNotUpdatedException){
            throw new ProductNotUpdatedException(String.format("Product with Id " + updateProduct.getId() + " Failed to Update") );
        }

    }

    @Override
    public void deleteProduct(int id) {
        Product product = getProductById(id);

        product.setDeleted(true);
        productRepository.save(product);
        logger.info(String.format("Product with ID "+product.getId()+" Deleted Successfully"));
    }

    @Override
    public List<Product> getAllAvailableProducts() {
        return productRepository.findAllAvailableProducts();
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFound(String.format("Product Not Found with ID: "+ id)));
    }

    @Override
    public Product getAvailableProductById(int id) {
        logger.info("{}", "product ID: "+id);
        return productRepository.findAvailableProductById(id)
                .orElseThrow(() -> new ProductNotFound(String.format("Product Not Found with ID: "+ id)));
    }

    // converting entity to dto
    public ProductDto convertEntityToDto(Product product){
        return mapper.map(product, ProductDto.class);
    }
    // converting dto to entity
    public Product convertDtoToEntity(ProductDto productDto){
        return mapper.map(productDto, Product.class);
    }
}
