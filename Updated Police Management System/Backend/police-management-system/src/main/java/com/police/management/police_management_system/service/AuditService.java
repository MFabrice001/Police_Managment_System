package com.police.management.police_management_system.service;

import com.police.management.police_management_system.Model.Audit;
import com.police.management.police_management_system.repository.AuditRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditService {

    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    /**
     * Saves an audit record with details about the entity action.
     *
     * @param entityName the name of the entity being changed
     * @param action the action performed (e.g., CREATE, UPDATE, DELETE)
     * @param changedBy the username of the user who made the change
     */
    public void saveAudit(String entityName, String action, String changedBy) {
        // Create a new audit record
        Audit audit = new Audit();
        audit.setEntityName(entityName);
        audit.setAction(action);
        audit.setChangedBy(changedBy);
        audit.setChangedAt(LocalDateTime.now());

        // Save the audit record, with basic exception handling
        try {
            auditRepository.save(audit);
        } catch (Exception e) {
            // Log the exception or handle it based on project requirements
            System.err.println("Error saving audit record: " + e.getMessage());
        }
    }
}
