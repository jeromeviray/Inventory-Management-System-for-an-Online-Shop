package com.project.inventory.store.product.category.repository;

import com.project.inventory.store.product.category.model.CategoriesWithTotalProductsDto;
import com.project.inventory.store.product.category.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    //    @Query(value = "SELECT category.id, category.category_name as categoryName, category.created_at as createdAt, category.is_deleted as deleted, COUNT(product.id) totalProducts  " +
//            "FROM product_categories as category " +
//            "LEFT JOIN products as product " +
//            "ON category.id = product.category_id " +
//            "WHERE category.category_name LIKE concat('%',:query,'%') AND category.is_deleted = 1 " +
//            "GROUP BY category.id \n#pageable\n",
////            countQuery = "SELECT COUNT(product.id) totalProducts " +
////                    "FROM product_categories as category " +
////                    "LEFT JOIN products as product " +
////                    "ON category.id = product.category_id " +
////                    "ORDER BY category.id ?#{#pageable}",
//            nativeQuery = true)
//    @Query(
//            value = "SELECT username, password, description, location, title, user_id FROM (institution INNER JOIN user ON institution.user_id = user.id) LEFT JOIN\n" +
//                    "(SELECT * FROM building_institutions WHERE building_institutions.building_id = 1) AS reserved_institutions\n" +
//                    "ON reserved_institutions.institutions_user_id = kits_nwt.institution.user_id\n" +
//                    "WHERE reserved_institutions.institutions_user_id IS null ORDER BY ?#{#pageable}",
//            nativeQuery = true)

    @Query(value = "SELECT category.id, category.category_name as categoryName, category.created_at as createdAt, category.is_deleted as deleted, COUNT(product.id) totalProducts " +
            "FROM product_categories as category " +
            "LEFT JOIN (SELECT * FROM products) as product " +
            "ON category.id = product.category_id " +
            "WHERE category.category_name LIKE concat('%',:query,'%') OR category.is_deleted = 0  " +
            "ORDER BY ?#{#pageable}", nativeQuery = true)
    Page<CategoriesWithTotalProductsDto> findAll( @Param( "query" ) String query, Pageable pageable );

    @Query( value = "SELECT * FROM product_categories WHERE category_name =:categoryName", nativeQuery = true )
    Category findByCategoryName( @Param( "categoryName" ) String categoryName );
}
