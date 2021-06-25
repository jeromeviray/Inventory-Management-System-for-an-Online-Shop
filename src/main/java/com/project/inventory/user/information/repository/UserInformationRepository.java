package com.project.inventory.user.information.repository;

import com.project.inventory.user.information.model.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInformationRepository extends JpaRepository<UserInformation, Integer> {

    Optional<UserInformation> findById(int id);
    Optional<UserInformation> findByAccountId(int accountId);
}
