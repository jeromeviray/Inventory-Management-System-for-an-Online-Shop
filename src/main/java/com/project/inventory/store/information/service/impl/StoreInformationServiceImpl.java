package com.project.inventory.store.information.service.impl;

import com.project.inventory.store.information.model.StoreInformation;
import com.project.inventory.store.information.model.StoreInformationDto;
import com.project.inventory.store.information.repository.StoreInformationRepository;
import com.project.inventory.store.information.service.StoreInformationService;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.model.ProductDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service( value = "storeInformationServiceImpl" )
public class StoreInformationServiceImpl implements StoreInformationService {
    Logger logger = LoggerFactory.getLogger( StoreInformationServiceImpl.class );

    @Autowired
    private StoreInformationRepository storeInformationRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<StoreInformation> saveStoreInformation( StoreInformation storeInformation ) {
        storeInformationRepository.save( storeInformation );
        return storeInformationRepository.findAll();
    }

    @Override
    public void updateStoreInformation( int id, StoreInformation storeInformation ) {

    }

    @Override
    public StoreInformation getStoreInformation( int id ) {
        return null;
    }

    @Override
    public StoreInformation getStoreInformationByBranch( String branch ) {
        return storeInformationRepository.findByBranch( branch )
                .orElseThrow();
    }

    @Override
    public StoreInformationDto getProductsByBranch( String branch ) {
        return convertEntityToDto( storeInformationRepository.findProductsByBranch( branch ) );
    }

    @Override
    public List<String> getStores() {
        List<String> location = new ArrayList<>();

        for ( StoreInformation store : storeInformationRepository.findAll() ) {
            location.add( store.getBranch() );
        }
        return location;
    }
    // converting entity to dto
    public StoreInformationDto convertEntityToDto( StoreInformation storeInformation ) {
        return mapper.map( storeInformation, StoreInformationDto.class );
    }

    // converting dto to entity
    public StoreInformation convertDtoToEntity( StoreInformationDto storeInformationDto ) {
        return mapper.map( storeInformationDto, StoreInformation.class );
    }
}
