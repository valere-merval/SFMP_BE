package com.smartfan.mobility.auth.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

public record RegisterRequest(
        @JsonAlias({"username"}) String email,
        String password,
        String firstName,
        String lastName,
        String country,
        String city,
        String phone,
        String preferredLanguage,
        String phonePrefix,
        String photoUrl,
        String accountType) {
}
