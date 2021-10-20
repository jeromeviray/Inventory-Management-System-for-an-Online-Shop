package com.project.inventory.store.website.carousel.repository;

import com.project.inventory.store.website.carousel.model.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarouselRepository extends JpaRepository<Carousel, Integer> {
}
