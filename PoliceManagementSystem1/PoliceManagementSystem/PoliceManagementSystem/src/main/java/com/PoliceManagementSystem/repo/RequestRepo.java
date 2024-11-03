package com.PoliceManagementSystem.repo;



import com.PoliceManagementSystem.Model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RequestRepo extends JpaRepository<Request, UUID> {

    Request findRequestsById(UUID id);
}
