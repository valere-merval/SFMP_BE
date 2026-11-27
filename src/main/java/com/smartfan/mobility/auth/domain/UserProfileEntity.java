package com.smartfan.mobility.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles", schema = "sfmp")
public class UserProfileEntity {

    @Id
    @Column(nullable = false, length = 255)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(name = "phone_prefix", nullable = false, length = 32)
    private String phonePrefix;

    @Column(nullable = false, length = 64)
    private String phone;

    @Column(name = "preferred_language", nullable = false, length = 32)
    private String preferredLanguage;

    @Column(name = "photo_url", columnDefinition = "text")
    private String photoUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected UserProfileEntity() {
    }

    public UserProfileEntity(String email, String firstName, String lastName, String country, String city, String phonePrefix, String phone, String preferredLanguage, String photoUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.city = city;
        this.phonePrefix = phonePrefix;
        this.phone = phone;
        this.preferredLanguage = preferredLanguage;
        this.photoUrl = photoUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getPhonePrefix() {
        return phonePrefix;
    }

    public String getPhone() {
        return phone;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void update(String firstName, String lastName, String country, String city, String phonePrefix, String phone, String preferredLanguage, String photoUrl, LocalDateTime updatedAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.city = city;
        this.phonePrefix = phonePrefix;
        this.phone = phone;
        this.preferredLanguage = preferredLanguage;
        this.photoUrl = photoUrl;
        this.updatedAt = updatedAt;
    }
}
