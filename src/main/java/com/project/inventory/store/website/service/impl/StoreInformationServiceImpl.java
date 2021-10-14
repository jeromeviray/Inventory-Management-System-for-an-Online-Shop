package com.project.inventory.store.website.service.impl;

import com.project.inventory.common.service.CommonService;
import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.product.model.FileImage;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.website.model.StoreInformation;
import com.project.inventory.store.website.repository.StoreInformationRepository;
import com.project.inventory.store.website.service.StoreInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class StoreInformationServiceImpl implements StoreInformationService {
    Logger logger = LoggerFactory.getLogger( StoreInformationServiceImpl.class );
    private final String logoFile = System.getProperty( "user.dir" ) +
            "/src/main/webapp/WEB-INF/inventory-management-system-reactjs/public/logo";
    @Autowired
    private StoreInformationRepository informationRepository;
    @Autowired
    private CommonService commonService;

    @Override
    public void saveStoreInformation( MultipartFile logoImage,
                                      String storeName,
                                      String domainName,
                                      String location,
                                      Object description ) throws IOException {
        StoreInformation storeInformation = new StoreInformation();
        storeInformation.setStoreName( storeName );
        storeInformation.setDomainName( domainName );
        storeInformation.setLocation( location );
        storeInformation.setDescription( ( String ) description );
        if ( logoImage != null ) {
            storeInformation.setLogo( getFileImage( logoImage ) );
        }
        informationRepository.save( storeInformation );
    }

    @Override
    public StoreInformation updateStoreInformation( int id,
                                                    MultipartFile logoImage,
                                                    String storeName,
                                                    String domainName,
                                                    String location,
                                                    Object description,
                                                    String removeLogo ) throws IOException {
//        return null;
        StoreInformation saveStoreInformation = getStoreInformationById( id );
        saveStoreInformation.setStoreName( storeName );
        saveStoreInformation.setDomainName( domainName );
        saveStoreInformation.setLocation( location );
        saveStoreInformation.setDescription( ( String ) description );
        if ( !removeLogo.equals( "" ) ) {
            deleteLogoImage( saveStoreInformation.getLogo() );
        }
        if ( logoImage != null ) {
            saveStoreInformation.setLogo( getFileImage( logoImage ) );
        }
        return informationRepository.save( saveStoreInformation );


    }

    @Override
    public StoreInformation getStoreInformation() {
        if(checkIfExist()){
            StoreInformation storeInformation = new StoreInformation();
            return informationRepository.save( storeInformation );
        }else{
            StoreInformation storeInformation = informationRepository.findAll().get( 0 );
            return getStoreInformationById( storeInformation.getId() );
        }
    }

    @Override
    public StoreInformation getStoreInformationById( int id ) {
        return informationRepository.findById( id ).orElseThrow( () -> new NotFoundException( "Store Information not found." ) );
    }

    @Override
    public boolean checkIfExist() {
        List<StoreInformation> stores = informationRepository.findAll();
        return stores.isEmpty();
    }

    public String getFileImage( MultipartFile file ) throws IOException {

        String imageName = "logo_" + commonService.generateStrings() + "_" + file.getOriginalFilename();
        Path path = Paths.get( logoFile, imageName );
        try {
            Files.write( path, file.getBytes() );
        } catch ( IOException e ) {
            e.printStackTrace();
            throw new MultipartException( "You got an Error men" );
        }

        return imageName;
    }

    private void deleteLogoImage( String fileName ) throws IOException {
        Path path = Paths.get( logoFile, fileName );
        try {
            // Delete file o
            Files.delete( path );
            logger.info( "File or directory deleted successfully" );
        } catch ( NoSuchFileException ex ) {
            throw new NoSuchFileException( "No such file or directory: +" + path );
        } catch ( DirectoryNotEmptyException ex ) {
            throw new DirectoryNotEmptyException( "Directory %s is not empty " + path );
        } catch ( IOException ex ) {
            throw new IOException( ex.getMessage() );
        }
    }
}