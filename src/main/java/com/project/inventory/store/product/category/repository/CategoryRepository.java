package com.project.inventory.store.product.category.repository;

import com.project.inventory.store.product.category.model.Category;
import com.project.inventory.store.product.category.model.GetCategoriesWithTotalProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT category.id, category.category_name as categoryName, category.created_at as createdAt, category.is_deleted as deleted, count(product.id) totalProducts " +
            "FROM product_categories as category " +
            "LEFT JOIN products as product " +
            "ON category.id = product.category_id " +
            "WHERE category.is_deleted = 0 " +
            "GROUP BY category.id", nativeQuery = true)
    List<GetCategoriesWithTotalProducts> countProductByCategoryId();

    @Query(value ="SELECT * FROM product_categories WHERE category_name =:categoryName", nativeQuery = true)
    Category findByCategoryName(@Param("categoryName") String categoryName);
}
