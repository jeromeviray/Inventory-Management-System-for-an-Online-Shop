package com.project.inventory.store.website.agreements.repository;

import com.project.inventory.store.website.agreements.model.TermsAndCondition;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TermsAndConditionRepository extends JpaRepository<TermsAndCondition, Integer> {
}
