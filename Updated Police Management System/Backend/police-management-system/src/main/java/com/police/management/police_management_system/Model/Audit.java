package com.police.management.police_management_system.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audits")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String entityName;

    @Column(nullable = false)
    private String action; // e.g., CREATE, UPDATE, DELETE

    @Column(nullable = false)
    private String changedBy; // The user who made the change

    @Column(nullable = false)
    private LocalDateTime changedAt; // Timestamp of the change

    // Default constructor
    public Audit() {}

    // Constructor with parameters
    public Audit(String entityName, String action, String changedBy, LocalDateTime changedAt) {
        this.entityName = entityName;
        this.action = action;
        this.changedBy = changedBy;
        this.changedAt = changedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    // Override toString for debugging
    @Override
    public String toString() {
        return "Audit{" +
                "id=" + id +
                ", entityName='" + entityName + '\'' +
                ", action='" + action + '\'' +
                ", changedBy='" + changedBy + '\'' +
                ", changedAt=" + changedAt +
                '}';
    }
}
