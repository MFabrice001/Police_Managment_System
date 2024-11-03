package com.PoliceManagementSystem.Service;

import com.PoliceManagementSystem.Model.Gun;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface GunService {
    Gun createGun(Gun gun);
    List<Gun> getAllGuns();
    Gun getGunById(UUID id);  // Add this method
    Page<Gun> getAllGunsPageable(int page, int size);
    void updateGun(Gun gun);
    void deleteGun(UUID id);

}
