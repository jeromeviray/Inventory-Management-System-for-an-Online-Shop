package com.project.inventory.store.website.agreements.service;

import com.project.inventory.store.website.agreements.model.TermsAndCondition;

import java.io.IOException;

public interface TermsAndConditionService {
    void saveTermsAndCondition( TermsAndCondition termsAndCondition );

    TermsAndCondition updateTermsAndCondition( int id,
                                             TermsAndCondition termsAndCondition );
    TermsAndCondition getTermsAndCondition();

    TermsAndCondition getTermsAndConditionById( int id );

    boolean checkIfExist();
}
