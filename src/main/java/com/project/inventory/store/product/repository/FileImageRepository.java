package com.project.inventory.store.product.repository;

import com.project.inventory.store.product.model.FileImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileImageRepository extends JpaRepository<FileImage, Integer> {

    FileImageRepository findById( int id);

    @Override
    <S extends FileImage> List<S> saveAllAndFlush( Iterable<S> iterable );

    FileImage findByFileNameAndProductId(String fileName, int productId);
}
