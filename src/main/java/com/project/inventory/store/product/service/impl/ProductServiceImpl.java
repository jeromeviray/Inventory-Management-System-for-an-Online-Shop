package com.project.inventory.store.product.service.impl;

import com.project.inventory.exception.invalid.InvalidException;
import com.project.inventory.exception.notFound.product.ProductNotFound;
import com.project.inventory.exception.serverError.product.ProductNotUpdatedException;
import com.project.inventory.store.information.branch.model.Branch;
import com.project.inventory.store.information.branch.model.GetBranchDto;
import com.project.inventory.store.information.branch.service.BranchService;
import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.service.InventoryService;
import com.project.inventory.store.inventory.stock.model.StockStatus;
import com.project.inventory.store.product.model.FileImage;
import com.project.inventory.store.product.model.FileImageDto;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.model.ProductDto;
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
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service( value = "productServiceImpl" )
public class ProductServiceImpl implements ProductService {
    Logger logger = LoggerFactory.getLogger( ProductServiceImpl.class );
    private final String rootFile = System.getProperty( "user.dir" ) +
            "/src/main/webapp/WEB-INF/inventory-management-system-reactjs/public/images/products";

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BranchService branchService;
    @Autowired
    private FileImageService fileImageService;
    @Autowired
    private ServletContext servletContext;

    @Override
    public Product saveProduct( MultipartFile[] files,
                                Product product,
                                String branch ) {
        if ( product != null ) {
            try {
                //get the branch
                Branch saveBranch = branchService.getBranchByBranch( branch );

                List<FileImage> fileImages = new ArrayList<>();
                // insert the saved branch on the product
                product.setBranch( saveBranch );
                // save the product
                Product savedProduct = productRepository.save( product );
                // after saving product, at same time the inventory save
                // a long with new product saved
                saveProductInventory( savedProduct );

                // after saving product and inventory
                // the image of the product will save also
                for ( FileImage fileImage : getFileImages( files ) ) {
                    if ( savedProduct != null ) {
                        fileImage.setProduct( savedProduct );
                        fileImages.add( fileImage );
                    }
                }
                fileImageService.saveFileImages( fileImages );
                return savedProduct;

            } catch ( InvalidException e ) {
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

    @Override
    public Product updateProduct( int id, Product updateProduct ) {
        Product product = getProductById( id ); // existing product in database

        product.setName( updateProduct.getName() );
        product.setDescription( updateProduct.getDescription() );
        product.setPrice( updateProduct.getPrice() );
        product.setDeleted( updateProduct.isDeleted() );

        try {
            return productRepository.save( product );
        } catch ( ProductNotUpdatedException productNotUpdatedException ) {
            throw new ProductNotUpdatedException( String.format( "Product with Id " + updateProduct.getId() + " Failed to Update" ) );
        }

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
        return productRepository.findById( id )
                .orElseThrow( () -> new ProductNotFound( String.format( "Product Not Found with ID: " + id ) ) );
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
        ProductDto productDto = new ProductDto();

        productDto.setId( product.getId() );
        productDto.setProductName( product.getName() );
        productDto.setProductPrice( product.getPrice() );
        productDto.setProductDescription( product.getDescription() );

        // convert file image to DTO

        productDto.setFileImages( getFileImageDto( product.getFileImages() ) );
        productDto.setBranch( getBranch( product.getBranch() ) );
        productDto.setInventory( inventoryService.convertEntityToDto( product.getInventory() ) );

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

    public List<FileImage> getFileImages( MultipartFile[] files ) {

        List<FileImage> fileImageList = new ArrayList<>();
        // reading all the image file and getting the details of the image
        for ( MultipartFile file : files ) {
            Path path = Paths.get( rootFile, file.getOriginalFilename() );
            String filename = file.getOriginalFilename();
            logger.info( file.getOriginalFilename() );

            try {
                Files.write( path, file.getBytes() );
            } catch ( IOException e ) {
                e.printStackTrace();
                throw new MultipartException( "You got an Error men" );
            }
            FileImage fileImage = new FileImage();
            fileImage.setFileName( filename );
            fileImage.setSize( file.getSize() );

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
    private List<FileImageDto> getFileImageDto(List<FileImage> fileImages){
        List<FileImageDto> fileImageDto = new ArrayList<>();
        for(FileImage fileImage : fileImages){
            fileImageDto.add( fileImageService.convertEntityToDto( fileImage ) );
        }
        return fileImageDto;
    }

    private GetBranchDto getBranch(Branch branch){
        GetBranchDto getBranchDto = new GetBranchDto();
        getBranchDto.setBranch( branch.getBranch() );
        return getBranchDto;
    }
}
