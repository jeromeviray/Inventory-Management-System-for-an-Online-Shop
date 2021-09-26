package com.project.inventory.store.product.category.service;

import com.project.inventory.store.product.category.model.Category;
import com.project.inventory.store.product.category.model.CategoryDto;
import com.project.inventory.store.product.category.model.GetCategoriesWithTotalProducts;

import java.util.List;

public interface CategoryService {

    void saveCategory( Category category );

    Category updateCategory( int id, Category category );

    void deleteCategory( int id );

    List<Category> getCategories();

    Category getCategory( int id );

    List<GetCategoriesWithTotalProducts> getCategoriesWithTotalProducts();

    CategoryDto convertEntityToDto(Category category);

    Category convertDtoToEntity(CategoryDto category);
}
