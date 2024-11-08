package com.police.management.police_management_system.repository;

import com.police.management.police_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return the User entity with the specified username, if found
     */
    User findByUsername(String username);

    /**
     * Finds a user by their reset token.
     *
     * @param token the reset token to search for
     * @return the User entity associated with the reset token, if found
     */
    User findByResetToken(String token);

    /**
     * Finds a user by their email.
     *
     * @param email the email to search for
     * @return the User entity with the specified email, if found
     */
    User findByEmail(String email);

    /**
     * Finds all users associated with a specific role name.
     *
     * @param roleName the name of the role to search for
     * @return a list of User entities associated with the specified role name
     */
    List<User> findByRoles_RoleName(String roleName); // Updated method
}