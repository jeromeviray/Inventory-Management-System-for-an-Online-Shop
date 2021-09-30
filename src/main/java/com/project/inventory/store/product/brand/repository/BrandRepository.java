package com.project.inventory.store.product.brand.repository;

import com.project.inventory.store.product.brand.model.Brand;
import com.project.inventory.store.product.brand.model.BrandsWithTotalProductsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Query(value = "SELECT * FROM brands WHERE is_deleted = 0", nativeQuery = true)
    List<Brand> findAll();

//    @Modifying
//    @Query(value = "SELECT * FROM brands WHERE is_deleted = 0 AND id =:id", nativeQuery = true)
//    Optional<Brand> findById( @Param( "id" ) int id );

    @Query(value ="SELECT * FROM brands WHERE brand_name =:brandName", nativeQuery = true)
    Brand findByBrandName(@Param("brandName") String brandName);

    Optional<Brand> findByIdAndIsDeleted(int id, boolean isDeleted);

//    @Query(value = "SELECT brand.id, brand.brand_name as brandName, brand.created_at as createdAt, brand.is_deleted as isDeleted, count(product.id) totalProducts " +
//            "FROM brands as brand " +
//            "LEFT JOIN products as product " +
//            "ON brand.id = product.brand_id " +
//            "WHERE brand.brand_name LIKE :query AND brand.is_deleted = 0 " +
//            "GROUP BY brand.id", nativeQuery = true)
//    Page<GetBrandsWithTotalProducts> findAllBrandAndProduct( @Param( "query" ) String query, Pageable pageable );

    @Query(value = "SELECT brand.id, brand.brand_name as brandName, brand.created_at as createdAt, brand.is_deleted as isDeleted, COUNT(product.id) totalProducts " +
            "FROM brands as brand " +
            "LEFT JOIN (SELECT * FROM products) as product " +
            "ON brand.id = product.brand_id " +
            "WHERE brand.brand_name LIKE concat('%',:query,'%') AND brand.is_deleted = 0 " ,
            countQuery = "SELECT COUNT(*) FROM brands",
            nativeQuery = true)
    Page<BrandsWithTotalProductsDto> findAll( @Param( "query" ) String query, Pageable pageable );
}
