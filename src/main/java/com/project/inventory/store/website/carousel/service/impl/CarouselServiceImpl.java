package com.project.inventory.store.website.carousel.service.impl;

import com.project.inventory.common.service.CommonService;
import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.website.carousel.model.Carousel;
import com.project.inventory.store.website.carousel.repository.CarouselRepository;
import com.project.inventory.store.website.carousel.service.CarouselService;
import com.project.inventory.store.website.service.StoreInformationService;
import com.project.inventory.webSecurity.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {
    private final String directoryName = "carousel-images";
    Logger logger = LoggerFactory.getLogger( CarouselServiceImpl.class );
    @Autowired
    private CarouselRepository carouselRepository;
    @Autowired
    private CommonService commonService;
    @Autowired
    private StoreInformationService storeInformationService;
    @Autowired
    private AppProperties appProperties;

    @Override
    public void saveCarouselImages( MultipartFile[] carouselImages ) {
        saveImages( carouselImages );
    }

    @Override
    public void updateCarouselImages( MultipartFile[] carouselImages, String[] removeImages ) throws IOException {
        if( removeImages != null ) {
            for( String removedImage : removeImages ) {
                Path path = Paths.get( appProperties.getFileImagePath()+directoryName, removedImage );
                deleteCarouselImageByImageName( removedImage );
                deleteFileImages( path );
            }
        }
        if( carouselImages != null ) {
            saveImages( carouselImages );
        }
    }

    @Override
    public List<Carousel> getCarouselImages() {
        return carouselRepository.findAll();
    }

    @Override
    public Carousel getCarouselByImageName( String name ) {
        return carouselRepository.findByImageName( name ).orElseThrow( () -> new NotFoundException( "Carousel not found." ) );
    }

    @Override
    public void deleteCarouselImageByImageName( String name ) {
        Carousel carousel = getCarouselByImageName( name );
        carouselRepository.deleteCarouseImageById( carousel.getId() );
    }

    private void saveImages( MultipartFile[] carouselImages ) {
        File directory = new File( appProperties.getFileImagePath() + directoryName );
        //folder for each product images
        if( !directory.exists() ) {
            directory.mkdir();
        }
        for( MultipartFile file : carouselImages ) {
            String imageName = "PR_" + commonService.generateStrings() + "_" + file.getOriginalFilename();
            Path path = Paths.get( appProperties.getFileImagePath() + directoryName, imageName );
//            String filename = file.getOriginalFilename();
            try {
                Files.write( path, file.getBytes() );
            } catch( IOException e ) {
                e.printStackTrace();
                throw new MultipartException( "Error occur " + e.getMessage() );
            }
            Carousel carousel = new Carousel();
            carousel.setImageName( imageName );
            carousel.setStoreInformation( storeInformationService.getStoreInformation() );
            carouselRepository.save( carousel );
        }
    }

    private void deleteFileImages( Path path ) throws IOException {
        try {
            // Delete file or directory
            Files.delete( path );
            logger.info( "File or directory deleted successfully" );
        } catch( NoSuchFileException ex ) {
            throw new NoSuchFileException( "No such file or directory: +" + path );
        } catch( DirectoryNotEmptyException ex ) {
            throw new DirectoryNotEmptyException( "Directory %s is not empty " + path );
        } catch( IOException ex ) {
            throw new IOException( ex.getMessage() );
        }
    }
}
