package com.project.inventory.store.product.service.impl;

import com.project.inventory.exception.invalid.InvalidException;
import com.project.inventory.exception.notFound.product.ProductNotFound;
import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.service.InventoryService;
import com.project.inventory.store.inventory.stock.model.Stock;
import com.project.inventory.store.inventory.stock.model.StockStatus;
import com.project.inventory.store.product.brand.model.Brand;
import com.project.inventory.store.product.brand.service.BrandService;
import com.project.inventory.store.product.category.model.Category;
import com.project.inventory.store.product.category.service.CategoryService;
import com.project.inventory.store.product.model.*;
import com.project.inventory.store.product.repository.ProductRepository;
import com.project.inventory.store.product.service.FileImageService;
import com.project.inventory.store.product.service.ProductService;
import org.apache.commons.io.IOUtils;
import org.hibernate.service.NullServiceException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service( value = "productServiceImpl" )
public class ProductServiceImpl implements ProductService {
    private final String rootFile = System.getProperty( "user.dir" ) +
            "/src/main/webapp/WEB-INF/inventory-management-system-reactjs/public/images/products/";
    Logger logger = LoggerFactory.getLogger( ProductServiceImpl.class );
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private FileImageService fileImageService;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;


    @Override
    public Product saveProduct( MultipartFile[] files,
                                Product product ) {
        if ( product != null ) {
            try {
                //get the branch
//                Branch saveBranch = branchService.getBranchByBranch( branch );

                List<FileImage> fileImages = new ArrayList<>();
                // insert the saved branch on the product
//                product.setBranch( saveBranch );
                // save the product
                Product savedProduct = productRepository.save( product );
                // after saving product, at same time the inventory save
                // a long with new product saved
                saveProductInventory( savedProduct );

                // after saving product and inventory
                // the image of the product will save also
                for ( FileImage fileImage : getFileImages( files, savedProduct ) ) {
                    if ( savedProduct != null ) {
                        fileImage.setProduct( savedProduct );
                        fileImages.add( fileImage );
                    }
                }
                fileImageService.saveFileImages( fileImages );
                return savedProduct;

            } catch ( InvalidException | IOException e ) {
                throw new InvalidException( "Unsuccessfully saved. Please Try Again!" );
            }
        } else {
            throw new NullServiceException( super.getClass() );
        }
    }

    public void saveProductInventory( Product product ) {
        // it will save the product in inventory when creating a product
        Inventory inventory = new Inventory();
        inventory.setProduct( product );
        inventory.setThreshold( 0 );
        inventory.setStatus( StockStatus.LOW );
        inventoryService.saveInventory( inventory );
    }

//    @Override
//    public Product updateProduct( int id, Product updateProduct ) {
//        Product product = getProductById( id ); // existing product in database
//
//        product.setName( updateProduct.getName() );
//        product.setDescription( updateProduct.getDescription() );
//        product.setPrice( updateProduct.getPrice() );
//        product.setDeleted( updateProduct.isDeleted() );
//
//        try {
//            return productRepository.save( product );
//        } catch ( ProductNotUpdatedException productNotUpdatedException ) {
//            throw new ProductNotUpdatedException( String.format( "Product with Id " + updateProduct.getId() + " Failed to Update" ) );
//        }
//
//    }


    @Override
    public Product updateProduct( int id,
                                  MultipartFile[] productImages,
                                  String productName,
                                  double productPrice,
                                  int barcode,
                                  Object productDescription,
                                  String brandName, String categoryName,
                                  String[] removedImages
    ) {
        Category category = categoryService.getCategoryByCategoryName( categoryName );
        Brand brand = brandService.getBrandByBrandName( brandName );
        Product product = getProductById( id );

        product.setBarcode( barcode );
        product.setName( productName );
        product.setDescription( ( String ) productDescription );
        product.setPrice( productPrice );
        product.setBrand( brand );
        product.setCategory( category );

//        for(MultipartFile multipartFile: productImages){
//            String filename = multipartFile.getOriginalFilename();
//
//        }
        return null;
    }

    @Override
    public void deleteProduct( int id ) {
        Product product = getProductById( id );

        product.setDeleted( true );
        productRepository.save( product );
        logger.info( String.format( "Product with ID " + product.getId() + " Deleted Successfully" ) );
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
    public Product getProductById( int id ) {

        Product product = productRepository.findById( id )
                .orElseThrow( () -> new ProductNotFound( String.format( "Product Not Found with ID: " + id ) ) );
        return product;
    }

    @Override
    public Product getAvailableProductById( int id ) {
        logger.info( "{}", "product ID: " + id );
        Product product = productRepository.findAvailableProductById( id )
                .orElseThrow( () -> new ProductNotFound( String.format( "Product Not Found with ID: " + id ) ) );

        return product;
    }

    // converting product entity to dto
    @Override
    public ProductDto convertEntityToDto( Product product ) {
//        logger.info( "{}", product.getBrand().getId() );
        ProductDto productDto = new ProductDto();

        productDto.setId( product.getId() );
        productDto.setProductName( product.getName() );
        productDto.setProductPrice( product.getPrice() );
        productDto.setProductDescription( product.getDescription() );
        productDto.setBarcode( product.getBarcode() );
        // convert file image to DTO

        productDto.setFileImages( getFileImageDto( product.getFileImages() ) );
//        productDto.setBranch( getBranch( product.getBranch() ) );
        GetInventory inventory = new GetInventory();
        inventory.setStatus( product.getInventory().getStatus().name() );
        inventory.setThreshold( product.getInventory().getThreshold() );
        inventory.setTotalStock( getTotalStocks( product ) );

        productDto.setInventory( inventory );

        return productDto;
    }

    // converting dto to entity
    @Override
    public Product convertDtoToEntity( ProductDto productDto ) {
        return mapper.map( productDto, Product.class );
    }

    @Override
    public byte[] getImage( String image ) throws IOException {
        try {
            InputStream readImage = servletContext.getResourceAsStream( "WEB-INF/inventory-management-system-reactjs/public/images/products/" + image );
            return IOUtils.toByteArray( readImage );
        } catch ( Exception e ) {
            throw e;
        }
    }

    public List<FileImage> getFileImages( MultipartFile[] files, Product product ) throws IOException {

        File directory = new File( rootFile+product.getId() );
        if(!directory.exists()){
            directory.mkdir();
        }
        List<FileImage> fileImageList = new ArrayList<>();
        // reading all the image file and getting the details of the image
        for ( MultipartFile file : files ) {
            Path path = Paths.get( rootFile + product.getId() , file.getOriginalFilename() );
            String filename = file.getOriginalFilename();
            try {
                Files.write( path, file.getBytes() );
            } catch ( IOException e ) {
                e.printStackTrace();
                throw new MultipartException( "You got an Error men" );
            }
            FileImage fileImage = new FileImage();
            fileImage.setFileName( filename );
            fileImage.setPath( Integer.toString( product.getId() )+"/" );
            fileImageList.add( fileImage );
            // checking when product is available on database
            // if not the saving of image for specific product will not save on database
//            if(product != null){
//                logger.info("image saving...");
//                fileImage.setProduct(product);
//                fileImageService.save(fileImage);
//
//                fileImageList.add(fileImage);
//            }

        }
        return fileImageList;
    }

    private List<FileImageDto> getFileImageDto( List<FileImage> fileImages ) {
        List<FileImageDto> fileImageDto = new ArrayList<>();
        for ( FileImage fileImage : fileImages ) {
            fileImageDto.add( fileImageService.convertEntityToDto( fileImage ) );
        }
        return fileImageDto;
    }

    @Override
    public int getTotalStocks( Product product ) {
        int sum = 0;
        Inventory inventory = inventoryService.getInventoryByProductId( product.getId() );

        for ( Stock stock : inventory.getStock() ) {
            if ( inventory.getId() == stock.getInventory().getId() ) {
                sum += stock.getStock();
            }
        }
        return sum;
    }
}
