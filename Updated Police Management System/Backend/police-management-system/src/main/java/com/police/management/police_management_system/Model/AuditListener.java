package com.police.management.police_management_system.Model;

import com.police.management.police_management_system.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class AuditListener {

    @Autowired
    private AuditService auditService;

    // Method called before a new entity is persisted
    @PrePersist
    public void onPrePersist(Object entity) {
        String entityName = entity.getClass().getSimpleName();
        String action = "CREATE"; // Action for new entities
        String changedBy = getCurrentUser(); // Retrieve the current user
        auditService.saveAudit(entityName, action, changedBy);
    }

    // Method called before an existing entity is updated
    @PreUpdate
    public void onPreUpdate(Object entity) {
        String entityName = entity.getClass().getSimpleName();
        String action = "UPDATE"; // Action for updated entities
        String changedBy = getCurrentUser(); // Retrieve the current user
        auditService.saveAudit(entityName, action, changedBy);
    }

    // Helper method to get the username of the currently authenticated user
    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && authentication.isAuthenticated())
                ? authentication.getName() // Get the username of the authenticated user
                : "System"; // Default to "System" if no user is authenticated
    }
}
