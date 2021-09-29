package com.project.inventory.store.product.repository;

import com.project.inventory.store.product.model.FileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface FileImageRepository extends JpaRepository<FileImage, Integer> {

    FileImageRepository findById( int id);

    @Override
    <S extends FileImage> List<S> saveAllAndFlush( Iterable<S> iterable );

    @Query(value = "SELECT * FROM file_images WHERE file_name =:fileName AND product_id =:productId", nativeQuery = true)
    Optional<FileImage> findByFileNameAndProductId( @Param("fileName") String fileName, @Param("productId") int productId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM file_images WHERE id =:id AND product_id =:productId", nativeQuery = true)
    void deleteByFileNameAndProductId(@Param("id") int id, @Param("productId") int productId);
//    void deleteById(int id);
}
