package com.materna.date4u.core.entities;

import jakarta.persistence.*;

@Entity
@Access(AccessType.FIELD)
public class Unicorn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @OneToOne
    @JoinColumn(name = "profile_fk")
    private Profile profile;

    public Unicorn() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Profile getProfile() {
        return profile;
    }

}
