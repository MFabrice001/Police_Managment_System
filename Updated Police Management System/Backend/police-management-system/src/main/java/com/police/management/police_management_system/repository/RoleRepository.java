package com.police.management.police_management_system.repository;

import com.police.management.police_management_system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a role by its name.
     *
     * @param roleName the name of the role to search for
     * @return an Optional containing the Role entity with the specified name, if found
     */
    Optional<Role> findByRoleName(String roleName);
}
