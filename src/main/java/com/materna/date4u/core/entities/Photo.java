package com.materna.date4u.core.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Access(AccessType.FIELD)
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_fk")
    private Profile profile;

    private String name;

    @Column(name = "is_profile_photo")
    private boolean isProfilePhoto;

    private LocalDateTime created;

    public Long getId() {
        return id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isProfilePhoto() {
        return isProfilePhoto;
    }

    public void setProfilePhoto(boolean profilePhoto) {
        isProfilePhoto = profilePhoto;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Photo[" + id + "]";
    }

}
