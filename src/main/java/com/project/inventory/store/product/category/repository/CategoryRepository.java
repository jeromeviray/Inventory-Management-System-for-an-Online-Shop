package com.project.inventory.store.product.category.repository;

import com.project.inventory.store.product.category.model.Category;
import com.project.inventory.store.product.category.model.GetCategoriesWithTotalProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "select category.id, category.category_name as categoryName, count(product.id) as totalProducts " +
            "FROM product_categories category " +
            "LEFT JOIN products as product " +
            "on category.id = product.category_id " +
            "GROUP BY category.id", nativeQuery = true)
    List<GetCategoriesWithTotalProducts> countProductByCategoryId();

}
