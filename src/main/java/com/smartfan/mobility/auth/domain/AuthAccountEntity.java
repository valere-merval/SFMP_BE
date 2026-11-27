package com.smartfan.mobility.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "auth_accounts", schema = "sfmp")
public class AuthAccountEntity {

    @Id
    @Column(length = 64, nullable = false, updatable = false)
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String organization;

    @Column(nullable = false, length = 512)
    private String rolesCsv;

    @Column(nullable = false)
    private String passwordHash;

    protected AuthAccountEntity() {
    }

    public AuthAccountEntity(String id, String email, String fullName, String role, String organization, List<String> roles, String passwordHash) {
        this.id = id;
        this.email = normalize(email);
        this.fullName = fullName;
        this.role = role;
        this.organization = organization;
        this.rolesCsv = String.join(",", roles == null ? List.of() : roles);
        this.passwordHash = passwordHash;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    public String getOrganization() {
        return organization;
    }

    public List<String> getRoles() {
        if (rolesCsv == null || rolesCsv.isBlank()) {
            return List.of();
        }
        return Arrays.stream(rolesCsv.split(","))
            .map(String::trim)
            .filter(value -> !value.isBlank())
            .toList();
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setRoles(List<String> roles) {
        this.rolesCsv = String.join(",", roles == null ? List.of() : roles);
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public AuthUser toUser() {
        return new AuthUser(id, email, fullName, role, organization, getRoles());
    }

    public static String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }
}
