package com.project.inventory.store.product.service.impl;

import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.service.AccountService;
import com.project.inventory.common.permission.service.AuthenticatedUser;
import com.project.inventory.exception.invalid.InvalidException;
import com.project.inventory.exception.notFound.product.ProductNotFound;
import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.model.InventoryDto;
import com.project.inventory.store.inventory.service.InventoryService;
import com.project.inventory.store.inventory.stock.model.StockStatus;
import com.project.inventory.store.product.brand.model.Brand;
import com.project.inventory.store.product.brand.service.BrandService;
import com.project.inventory.store.product.category.model.Category;
import com.project.inventory.store.product.category.service.CategoryService;
import com.project.inventory.store.product.model.*;
import com.project.inventory.store.product.promo.model.Promo;
import com.project.inventory.store.product.promo.service.PromoService;
import com.project.inventory.store.product.repository.ProductRepository;
import com.project.inventory.store.product.service.FileImageService;
import com.project.inventory.store.product.service.ProductService;
import com.project.inventory.store.product.wishlist.model.Wishlist;
import com.project.inventory.store.product.wishlist.service.WishlistService;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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
    @Autowired
    private AccountService accountService;
    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Autowired
    private PromoService promoService;

    @Override
    public Product saveProduct( MultipartFile[] productImages,
                                String productName,
                                double productPrice,
                                String barcode,
                                Object productDescription,
                                String brandName,
                                String categoryName ) {
        logger.info( "{}", barcode );
        Product product = new Product();
        product.setName( productName );
        product.setPrice( productPrice );
        product.setDescription( ( String ) productDescription );
        product.setBarcode( barcode );
        product.setBrand( brandService.getBrandByBrandName( brandName ) );
        product.setCategory( categoryService.getCategoryByCategoryName( categoryName ) );
        try {

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
            if ( productImages != null ) {
                for ( FileImage fileImage : getFileImages( productImages, savedProduct ) ) {
                    if ( savedProduct != null ) {
                        fileImage.setProduct( savedProduct );
                        fileImages.add( fileImage );
                    }
                }
                fileImageService.saveFileImages( fileImages );
            }

            return savedProduct;

        } catch ( InvalidException | IOException e ) {
            throw new InvalidException( "Unsuccessfully saved. Please Try Again!" );
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
    public Product updateProduct( int id,
                                  MultipartFile[] productImages,
                                  String productName,
                                  double productPrice,
                                  String barcode,
                                  Object productDescription,
                                  String brandName, String categoryName,
                                  String[] removedImages
    ) throws Exception {
        Category category = categoryService.getCategoryByCategoryName( categoryName );
        Brand brand = brandService.getBrandByBrandName( brandName );
        Product product = getProductById( id );

        product.setBarcode( barcode );
        product.setName( productName );
        product.setDescription( ( String ) productDescription );
        product.setPrice( productPrice );
        product.setBrand( brand );
        product.setCategory( category );
        Product savedProduct = productRepository.save( product );
        //remove the image in directory and database
        if ( removedImages != null ) {
            for ( String removedImage : removedImages ) {
                logger.info( "{}", removedImage );
                FileImage fileImage = fileImageService.getFileImageByFileNameAndProductId( removedImage, savedProduct.getId() );
                if ( fileImage != null ) {
                    Path path = Paths.get( rootFile + product.getId(), removedImage );
                    fileImageService.deleteFileImage( fileImage, path, product.getId() );
                }
            }
        }
        // add the new Images
        List<FileImage> fileImages = new ArrayList<>();
        if ( productImages != null ) {
            for ( FileImage fileImage : getFileImages( productImages, savedProduct ) ) {
                fileImage.setProduct( savedProduct );
                fileImages.add( fileImage );
            }
            fileImageService.saveFileImages( fileImages );
        }
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
    public Page<ProductAndInventoryDto> getProducts( String query, Pageable pageable ) throws ParseException {

        try {
            List<ProductAndInventoryDto> productRecordByPages = new ArrayList<>();
            Page<Product> products = productRepository.findAll( query, pageable );

            for ( Product product : products.getContent() ) {
                ProductAndInventoryDto productAndInventory = getProductAndInventoryByProductId( product.getId() );
//                InventoryDto inventory = inventoryService.convertEntityToDto( product.getInventory() );
//                productAndInventory.setProduct( convertEntityToDto( product ) );
//                productAndInventory.setInventory( inventory );
//
//                if ( product.getPromo() != null ) {
//                    Promo promo = product.getPromo();
//                    promo.setStatus( promoService.checkSchedulePromo( promo ) );
//                    promoService.updatePromo( promo.getId(), promo );
//                    productAndInventory.setPromo( promoService.convertEntityToDto( promo ) );
//                }
                productRecordByPages.add( productAndInventory );
            }
            return new PageImpl<>( productRecordByPages, pageable, products.getTotalElements() );
        } catch ( Exception e ) {
            throw e;
        }
    }
    @Override
    public Page<ProductAndInventoryDto> getProductByCategoryName( String categoryName, String query, Pageable pageable ) throws ParseException {
        try {
            List<ProductAndInventoryDto> productRecordByPages = new ArrayList<>();
            Page<Product> products = productRepository.findAllProductsByCategoryName( categoryName, query, pageable );
            for ( Product product : products.getContent() ) {
                ProductAndInventoryDto productAndInventory = getProductAndInventoryByProductId( product.getId() );
//                InventoryDto inventory = inventoryService.convertEntityToDto( product.getInventory() );
//                productAndInventory.setProduct( convertEntityToDto( product ) );
//                productAndInventory.setInventory( inventory );
//                try {
//                    Account account = authenticatedUser.getUserDetails();
//                    logger.info( "{}", account.getId() );
//                    Wishlist wishlist = wishlistService.getWishlistByProductId( account.getId(), product.getId() );
//                    productAndInventory.setWishlist( wishlist );
//                } catch ( Exception e ) {
//                    ///
//                }
//                if ( product.getPromo() != null ) {
//                    Promo promo = product.getPromo();
//                    promo.setStatus( promoService.checkSchedulePromo( promo ) );
//                    promoService.updatePromo( promo.getId(), promo );
//                    productAndInventory.setPromo( promoService.convertEntityToDto( promo ) );
//                }
                productRecordByPages.add( productAndInventory );
            }
            return new PageImpl<>( productRecordByPages, pageable, products.getTotalElements() );
        } catch ( Exception e ) {
            throw e;
        }
    }

    @Override
    public Page<ProductAndInventoryDto> getProductByStatus( String query, String status, Pageable pageable ) {
        try {
            List<ProductAndInventoryDto> productRecordByPages = new ArrayList<>();
            Page<Product> products;
            if ( !Objects.equals( status, "" ) ) {
                products = productRepository.findAllProductsByStatus( query, status, pageable );
            } else {
                products = productRepository.findAll( query, pageable );
            }
            for ( Product product : products.getContent() ) {
                ProductAndInventoryDto productAndInventory = new ProductAndInventoryDto();
                InventoryDto inventory = inventoryService.convertEntityToDto( product.getInventory() );
                productAndInventory.setProduct( convertEntityToDto( product ) );
                productAndInventory.setInventory( inventory );
                productRecordByPages.add( productAndInventory );
            }
            return new PageImpl<>( productRecordByPages, pageable, products.getTotalElements() );
        } catch ( Exception e ) {
            throw e;
        }
    }

    @Override
    public Page<ProductAndInventoryDto> getProductsWithPromo( String query, String status, Pageable pageable ) throws ParseException {
        try {
            List<ProductAndInventoryDto> productRecordByPages = new ArrayList<>();
            Page<Product> products;
            if ( !Objects.equals( status, "" ) ) {
                products = productRepository.findAllProductsWithPromoAndStatus( query, status, pageable );
            } else {
                products = productRepository.findAllProductsWithPromo( query, pageable );
            }
            for ( Product product : products.getContent() ) {
                ProductAndInventoryDto productAndInventory = new ProductAndInventoryDto();
                productAndInventory.setProduct( convertEntityToDto( product ) );
                productAndInventory.setInventory( inventoryService.convertEntityToDto( product.getInventory() ) );
                Promo promo = product.getPromo();
                promo.setStatus( promoService.checkSchedulePromo( promo ) );
                promoService.updatePromo( promo.getId(), promo );
                productAndInventory.setPromo( promoService.convertEntityToDto( promo ) );
                productRecordByPages.add( productAndInventory );
            }
            return new PageImpl<>( productRecordByPages, pageable, products.getTotalElements() );
        } catch ( Exception e ) {
            throw e;
        }
    }
    @Override
    public Product getProductById( int id ) {

        Product product = productRepository.findById( id )
                .orElseThrow( () -> new ProductNotFound( String.format( "Product Not Found with ID: " + id ) ) );
        return product;
    }

    @Override
    public ProductAndInventoryDto getProductAndInventoryByProductId( int id ) throws ParseException {
        ProductAndInventoryDto productAndInventoryDto = new ProductAndInventoryDto();
        Product product = getProductById( id );
        try {
            Account account = authenticatedUser.getUserDetails();
            Wishlist wishlist = wishlistService.getWishlistByProductId( account.getId(), id );
            if ( wishlist != null ) {
                productAndInventoryDto.setWishlist( wishlist );
            }
        } catch ( Exception e ) {
            ///
        }

        Promo promo = promoService.getPromoByProductId( product.getId(), "ONGOING" );
        if ( promo != null ) {
            promo.setStatus( promoService.checkSchedulePromo( promo ) );
            promoService.updatePromo( promo.getId(), promo );
            productAndInventoryDto.setPromo( promoService.convertEntityToDto( promo ) );
        }
        productAndInventoryDto.setProduct( convertEntityToDto( product ) );
        productAndInventoryDto.setInventory( inventoryService.convertEntityToDto( product.getInventory() ) );
        return productAndInventoryDto;
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
        return mapper.map( product, ProductDto.class );
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

        File directory = new File( rootFile + product.getId() );
        //folder for each product images
        if ( !directory.exists() ) {
            directory.mkdir();
        }
        List<FileImage> fileImageList = new ArrayList<>();
        // reading all the image file and getting the details of the image
        for ( MultipartFile file : files ) {
            String imageName = "PR_" + generateStrings() + "_" + file.getOriginalFilename();
            Path path = Paths.get( rootFile + product.getId(), imageName );
//            String filename = file.getOriginalFilename();
            try {
                Files.write( path, file.getBytes() );
            } catch ( IOException e ) {
                e.printStackTrace();
                throw new MultipartException( "You got an Error men" );
            }
            FileImage fileImage = new FileImage();
            fileImage.setFileName( imageName );
            fileImage.setPath( product.getId() + "/" );
            fileImageList.add( fileImage );

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

    private String generateStrings() {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk";
        Random rnd = new Random();
        StringBuilder randomString = new StringBuilder( 5 );
        for ( int i = 0; i < 5; i++ ) {
            randomString.append( chars.charAt( rnd.nextInt( chars.length() ) ) );
        }
        return randomString.toString();
    }
}
