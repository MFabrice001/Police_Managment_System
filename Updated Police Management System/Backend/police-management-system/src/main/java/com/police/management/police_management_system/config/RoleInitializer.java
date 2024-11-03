package com.police.management.police_management_system.config;

import com.police.management.police_management_system.entity.Role;
import com.police.management.police_management_system.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {

    private static final Logger logger = LoggerFactory.getLogger(RoleInitializer.class);

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            try {
                initializeRole(roleRepository, "ADMIN");
                initializeRole(roleRepository, "STAFF");
                initializeRole(roleRepository, "MANAGER");
            } catch (Exception e) {
                logger.error("An error occurred while initializing roles: ", e);
            }
        };
    }

    private void initializeRole(RoleRepository roleRepository, String roleName) {
        if (roleRepository.findByRoleName(roleName) == null) {
            roleRepository.save(new Role(roleName));
            logger.info("Created role: " + roleName);
        } else {
            logger.info("Role already exists: " + roleName);
        }
    }
}
