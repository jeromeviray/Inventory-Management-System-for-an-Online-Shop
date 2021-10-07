package com.project.inventory.store.product.category.service;

import com.project.inventory.store.product.category.model.Category;
import com.project.inventory.store.product.category.model.CategoryDto;
import com.project.inventory.store.product.category.model.CategoriesWithTotalProductsDto;
import com.project.inventory.store.product.category.model.CategoryListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    void saveCategory( Category category );

    Category updateCategory( int id, Category category );

    void deleteCategory( int id );

    List<CategoryListDto> getCategories();

    Category getCategory( int id );

    Category getCategoryByCategoryName(String categoryName);

    Page<CategoriesWithTotalProductsDto> getCategoriesWithTotalProducts( String query, Pageable pageable );

    CategoryDto convertEntityToDto(Category category);

    Category convertDtoToEntity(CategoryDto category);

}
