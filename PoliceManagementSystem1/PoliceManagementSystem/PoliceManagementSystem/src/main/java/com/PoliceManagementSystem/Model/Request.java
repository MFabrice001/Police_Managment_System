package com.PoliceManagementSystem.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.UUID;
@Data
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "gun_id")
    private Gun gun;

    @Temporal(TemporalType.DATE)
    private Date requestdate;

    @ManyToOne
    @JoinColumn(name = "signup_id")
    private Signup signup;

    @OneToMany(mappedBy = "request")
    private List<Returns> returns;

    // Add a constructor to initialize Signup
    public Request() {
        this.signup = new Signup();
    }
}
