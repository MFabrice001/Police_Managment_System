package com.PoliceManagementSystem.repo;



import com.PoliceManagementSystem.Model.Gun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GunRepo extends JpaRepository<Gun, UUID> {

    Gun findGunById(UUID id);
    Gun findGunByRegistrationNumber(String registrationNumber);
}
