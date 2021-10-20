package com.project.inventory.store.website.carousel.service;

import com.project.inventory.store.website.carousel.model.Carousel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarouselService {

    void saveCarouselImages( MultipartFile[] carouselImages );

    void updateCarouselImages( MultipartFile[] carouselImages, String removeImages );

    List<Carousel> getCarouselImages();

    Carousel getCarouselById(int id);
}
