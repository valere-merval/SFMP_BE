package com.smartfan.mobility.auth.api;

public record UserProfileRequest(
        String firstName,
        String lastName,
        String country,
        String city,
        String phonePrefix,
        String phone,
        String preferredLanguage,
        String photoUrl) {
}
