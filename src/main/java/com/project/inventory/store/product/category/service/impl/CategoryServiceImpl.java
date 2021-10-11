package com.project.inventory.store.product.category.service.impl;

import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.product.category.model.Category;
import com.project.inventory.store.product.category.model.CategoryDto;
import com.project.inventory.store.product.category.model.CategoriesWithTotalProductsDto;
import com.project.inventory.store.product.category.model.CategoryListDto;
import com.project.inventory.store.product.category.repository.CategoryRepository;
import com.project.inventory.store.product.category.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    Logger logger = LoggerFactory.getLogger( CategoryServiceImpl.class );
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public void saveCategory( Category category ) {
        try {
            categoryRepository.save( category );
        }catch( Exception e ){
            throw e;
        }
    }

    @Override
    public Category updateCategory( int id, Category category ) {
        try {
            Category savedCategory = getCategory( id );
            savedCategory.setName( category.getName() );
            return categoryRepository.save( savedCategory );
        }catch( Exception e ){
            throw e;
        }
    }

    @Override
    public void deleteCategory( int id ) {
        try {
            Category category = getCategory( id );
            category.setDeleted( true );
            saveCategory( category );
        }catch( Exception e ){
            throw e;
        }
    }

    @Override
    public List<CategoryListDto> getCategories() {
        return categoryRepository.findAllCategory();
    }

    @Override
    public Category getCategory( int id ) {
        return categoryRepository.findById( id ).orElseThrow(() -> new NotFoundException("Category Not Found.") );
    }

    @Override
    public Category getCategoryByCategoryName( String categoryName ) {
        return categoryRepository.findByCategoryName( categoryName );
    }

    @Override
    public Page<CategoriesWithTotalProductsDto> getCategoriesWithTotalProducts( String query, Pageable pageable ) {
        try {
            Page<CategoriesWithTotalProductsDto> categories = categoryRepository.findAll(query, pageable);
            List<CategoriesWithTotalProductsDto> categoryRecordsByPage = new ArrayList<>();
            for(CategoriesWithTotalProductsDto categoriesWithTotalProductsDto : categories.getContent()){
                categoryRecordsByPage.add( categoriesWithTotalProductsDto );
            }
            return new PageImpl<>( categoryRecordsByPage, pageable, categories.getTotalElements()  );
        }catch( Exception e ){
            throw e;
        }
    }

    @Override
    public CategoryDto convertEntityToDto( Category category ) {
        return mapper.map( category, CategoryDto.class );
    }

    @Override
    public Category convertDtoToEntity( CategoryDto category ) {
        return mapper.map( category, Category.class );
    }
}
