package com.PoliceManagementSystem.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Signup {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String email;
    @Column(name = "`rank`")
    @Enumerated(EnumType.STRING)
    private Rank rank;
    private Integer policeNumber;
    private String password;

    @OneToMany(mappedBy = "signup")
    private List<Request> requests;


}
