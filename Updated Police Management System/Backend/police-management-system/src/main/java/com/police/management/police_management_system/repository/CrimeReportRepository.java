package com.police.management.police_management_system.repository;

import com.police.management.police_management_system.entity.CrimeReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CrimeReportRepository extends JpaRepository<CrimeReport, Long> {

    /**
     * Finds crime reports associated with a specific user.
     *
     * @param userId the ID of the user
     * @return a list of crime reports submitted by the specified user
     */
    List<CrimeReport> findByUserId(Long userId);

    /**
     * Finds crime reports located in a specific area.
     *
     * @param location the location of the crime
     * @return a list of crime reports from the specified location
     */
    List<CrimeReport> findByLocation(String location);

    /**
     * Finds crime reports containing a specific keyword in the description.
     *
     * @param keyword the keyword to search within the crime description
     * @return a list of crime reports that contain the keyword
     */
    List<CrimeReport> findByDescriptionContaining(String keyword);

    /**
     * Finds crime reports reported between specified dates.
     *
     * @param startDate the start date of the reporting range
     * @param endDate   the end date of the reporting range
     * @return a list of crime reports reported within the specified date range
     */
    List<CrimeReport> findByDateReportedBetween(LocalDateTime startDate, LocalDateTime endDate);
}
