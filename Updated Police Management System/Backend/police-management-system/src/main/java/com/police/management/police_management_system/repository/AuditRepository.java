package com.police.management.police_management_system.repository;

import com.police.management.police_management_system.Model.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for accessing and managing audit records.
 */
public interface AuditRepository extends JpaRepository<Audit, Long> {

    /**
     * Finds all audit records by entity name.
     *
     * @param entityName the name of the entity being audited
     * @return a list of audit records for the specified entity name
     */
    List<Audit> findByEntityName(String entityName);

    /**
     * Finds all audit records for changes made by a specific user.
     *
     * @param changedBy the username of the user who made the changes
     * @return a list of audit records changed by the specified user
     */
    List<Audit> findByChangedBy(String changedBy);

    /**
     * Finds audit records within a specific date range.
     *
     * @param startDate the start date and time of the range
     * @param endDate the end date and time of the range
     * @return a list of audit records that were changed between the start and end dates
     */
    List<Audit> findByChangedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Finds all audit records with pagination support.
     *
     * @param pageable pagination and sorting information
     * @return a paginated list of audit records
     */
    Page<Audit> findAll(Pageable pageable);
}
