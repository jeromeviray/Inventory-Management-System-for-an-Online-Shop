package com.project.inventory.store.website.carousel.service.impl;

import com.project.inventory.common.service.CommonService;
import com.project.inventory.store.product.model.FileImage;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.website.carousel.model.Carousel;
import com.project.inventory.store.website.carousel.repository.CarouselRepository;
import com.project.inventory.store.website.carousel.service.CarouselService;
import com.project.inventory.store.website.service.StoreInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {
    Logger logger = LoggerFactory.getLogger( CarouselServiceImpl.class );
    private final String rootFile = System.getProperty("user.dir")+"/src/main/webapp/WEB-INF/reactjs/public/carousel";
    @Autowired
    private CarouselRepository carouselRepository;
    @Autowired
    private CommonService commonService;
    @Autowired
    private StoreInformationService storeInformationService;

    @Override
    public void saveCarouselImages( MultipartFile[] carouselImages ) {
        for ( MultipartFile file : carouselImages ) {
            String imageName = "PR_" +commonService.generateStrings() + "_" + file.getOriginalFilename();
            Path path = Paths.get( rootFile, imageName );
//            String filename = file.getOriginalFilename();
            try {
                Files.write( path, file.getBytes() );
            } catch ( IOException e ) {
                e.printStackTrace();
                throw new MultipartException( "Error occur "+e.getMessage() );
            }
            Carousel carousel = new Carousel();
            carousel.setImageName( imageName );
            carousel.setStoreInformation(storeInformationService.getStoreInformation()  );
            carouselRepository.save(carousel  );
        }
    }

    @Override
    public void updateCarouselImages( MultipartFile[] carouselImages, String removeImages ) {

    }

    @Override
    public List<Carousel> getCarouselImages() {
        return carouselRepository.findAll();
    }

    @Override
    public Carousel getCarouselById( int id ) {
        return null;
    }
}
