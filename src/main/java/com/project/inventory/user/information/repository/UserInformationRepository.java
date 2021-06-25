package com.project.inventory.user.information.repository;

import com.project.inventory.user.information.model.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInformationRepository extends JpaRepository<UserInformation, Integer> {
}
