package com.smartfan.mobility.auth.api;

import java.time.LocalDateTime;

public record UserProfileView(
        String email,
        String firstName,
        String lastName,
        String country,
        String city,
        String phonePrefix,
        String phone,
        String preferredLanguage,
        String photoUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
