package com.PoliceManagementSystem.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Returns {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    private int returnBullets;
    private int returnMagazine;
}

