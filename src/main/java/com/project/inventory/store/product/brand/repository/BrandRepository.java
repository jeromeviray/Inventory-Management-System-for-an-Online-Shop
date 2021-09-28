package com.project.inventory.store.product.brand.repository;

import com.project.inventory.store.product.brand.model.Brand;
import com.project.inventory.store.product.brand.model.GetBrandsWithTotalProducts;
import com.project.inventory.store.product.category.model.GetCategoriesWithTotalProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
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

    @Query(value = "SELECT brand.id, brand.brand_name as brandName, brand.created_at as createdAt, brand.is_deleted as isDeleted, count(product.id) totalProducts " +
            "FROM brands as brand " +
            "LEFT JOIN products as product " +
            "ON brand.id = product.brand_id " +
            "WHERE brand.is_deleted = 0 " +
            "GROUP BY brand.id", nativeQuery = true)
    List<GetBrandsWithTotalProducts> countProductByBrandId();
}
