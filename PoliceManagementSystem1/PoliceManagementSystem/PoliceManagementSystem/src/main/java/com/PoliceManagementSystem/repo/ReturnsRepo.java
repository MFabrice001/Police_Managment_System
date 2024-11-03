package com.PoliceManagementSystem.repo;



import com.PoliceManagementSystem.Model.Returns;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReturnsRepo extends JpaRepository<Returns, UUID> {

    Returns findReturnsById(UUID id);

}

