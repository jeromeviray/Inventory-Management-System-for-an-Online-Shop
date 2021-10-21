package com.project.inventory.store.website.carousel.repository;

import com.project.inventory.store.website.carousel.model.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CarouselRepository extends JpaRepository<Carousel, Integer> {

    @Query(value = "SELECT * FROM carousel_images WHERE image_name =:name", nativeQuery = true)
    Optional<Carousel> findByImageName( @Param( "name" ) String name);

    @Transactional
    @Modifying
    @Query(value = "delete from inventory_system.carousel_images " +
            "where id =:id", nativeQuery = true)
    void deleteCarouseImageById(@Param( "id" ) int id);
}
