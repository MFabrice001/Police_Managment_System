package com.police.management.police_management_system.repository;

import com.police.management.police_management_system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a role by its name.
     *
     * @param name the name of the role to search for
     * @return the Role entity with the specified name, if found
     */
    Role findByName(String name);
}
