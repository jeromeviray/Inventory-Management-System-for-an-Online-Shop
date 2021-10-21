package com.project.inventory.store.website.carousel.service;

import com.project.inventory.store.website.carousel.model.Carousel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CarouselService {

    void saveCarouselImages( MultipartFile[] carouselImages );

    void updateCarouselImages( MultipartFile[] carouselImages, String[] removeImages ) throws IOException;

    List<Carousel> getCarouselImages();

    Carousel getCarouselByImageName( String name );

    void deleteCarouselImageByImageName(String name);
}
