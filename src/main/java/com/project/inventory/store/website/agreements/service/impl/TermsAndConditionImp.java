package com.project.inventory.store.website.agreements.service.impl;

import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.website.agreements.model.TermsAndCondition;
import com.project.inventory.store.website.agreements.repository.TermsAndConditionRepository;
import com.project.inventory.store.website.agreements.service.TermsAndConditionService;
import com.project.inventory.store.website.model.StoreInformation;
import com.project.inventory.store.website.service.StoreInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermsAndConditionImp implements TermsAndConditionService {
    Logger logger = LoggerFactory.getLogger( TermsAndConditionImp.class );

    @Autowired
    private TermsAndConditionRepository termsAndConditionRepository;
    @Autowired
    private StoreInformationService storeInformationService;

    @Override
    public void saveTermsAndCondition( TermsAndCondition termsAndCondition ) {
        try {
            StoreInformation storeInformation = storeInformationService.getStoreInformation();
            termsAndCondition.setStoreInformation( storeInformation );
            termsAndConditionRepository.save( termsAndCondition );
        } catch( Exception e ) {
            throw e;
        }
    }

    @Override
    public TermsAndCondition updateTermsAndCondition( int id, TermsAndCondition termsAndCondition ) {
        try {
            TermsAndCondition savedTermsAndCondition = getTermsAndConditionById( id );
            savedTermsAndCondition.setContent( termsAndCondition.getContent() );
            return termsAndConditionRepository.save( termsAndCondition );
        } catch( Exception e ) {
            throw e;
        }
    }

    @Override
    public TermsAndCondition getTermsAndCondition() {
        if( checkIfExist() ) {
            return null;
        } else {
            TermsAndCondition termsAndCondition = termsAndConditionRepository.findAll().get( 0 );
            return getTermsAndConditionById( termsAndCondition.getId() );
        }
    }

    @Override
    public TermsAndCondition getTermsAndConditionById( int id ) {
        return termsAndConditionRepository.findById( id ).orElseThrow( () -> new NotFoundException( "Store Information not found." ) );
    }

    @Override
    public boolean checkIfExist() {
        List<TermsAndCondition> termsAndConditions = termsAndConditionRepository.findAll();

        return termsAndConditions.isEmpty();
    }
}
