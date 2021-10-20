package com.project.inventory.store.website.carousel.controller;

import com.project.inventory.store.website.carousel.service.CarouselService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;

@RestController
@RequestMapping(value = "/api/v1/carousel")
public class CarouselController {
    private final String rootFile = System.getProperty("user.dir")+"/src/main/webapp/WEB-INF/reactjs/public/carousel";
    Logger logger = LoggerFactory.getLogger( CarouselController.class );

    @Autowired
    private CarouselService carouselService;

    @PreAuthorize( "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/save", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } )
    public ResponseEntity<?> saveCarouselImages( @RequestPart( value = "carouselImages[]", required = false ) MultipartFile[] carouselImages){
        carouselService.saveCarouselImages( carouselImages );

        return new ResponseEntity<>( HttpStatus.OK );
    }
    @RequestMapping( value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getCarouselImages(){
        return new ResponseEntity<>(carouselService.getCarouselImages(), HttpStatus.OK );
    }
    @RequestMapping( value = "/getImages/bytesArrays/{image}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE )
    public ResponseEntity<?> getImage( @PathVariable String image )  {
        try {
            InputStream readImage = new FileInputStream( rootFile+"/" + image );
            return new ResponseEntity( IOUtils.toByteArray( readImage ), HttpStatus.OK );
        } catch ( Exception e ) {
            logger.info(e.getMessage());
        }

        return new ResponseEntity( HttpStatus.OK );
    }

}
