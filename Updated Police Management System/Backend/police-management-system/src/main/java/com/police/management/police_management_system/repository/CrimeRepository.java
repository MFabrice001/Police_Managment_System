package com.police.management.police_management_system.repository;

import com.police.management.police_management_system.model.Crime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrimeRepository extends JpaRepository<Crime, Long> {
}
