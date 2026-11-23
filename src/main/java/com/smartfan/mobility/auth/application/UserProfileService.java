package com.smartfan.mobility.auth.application;

import com.smartfan.mobility.auth.api.UserProfileRequest;
import com.smartfan.mobility.auth.api.UserProfileView;
import com.smartfan.mobility.auth.domain.RegisterRequest;
import com.smartfan.mobility.auth.domain.UserProfileEntity;
import com.smartfan.mobility.auth.domain.UserProfileRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    private final UserProfileRepository repository;

    public UserProfileService(UserProfileRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void saveProfile(String email, RegisterRequest request) {
        upsert(email, new UserProfileRequest(
                request.firstName(),
                request.lastName(),
                request.country(),
                request.city(),
                request.phonePrefix(),
                request.phone(),
                request.preferredLanguage(),
                request.photoUrl()));
    }

    @Transactional(readOnly = true)
    public UserProfileView me(String email) {
        return repository.findById(email)
                .map(this::toView)
                .orElseGet(() -> new UserProfileView(email, "", "", "", "", "", "", "en", null, null, null));
    }

    @Transactional
    public UserProfileView upsert(String email, UserProfileRequest request) {
        LocalDateTime now = LocalDateTime.now();
        UserProfileEntity profile = repository.findById(email)
                .orElseGet(() -> new UserProfileEntity(
                        email,
                        text(request.firstName(), email),
                        text(request.lastName(), email),
                        text(request.country(), ""),
                        text(request.city(), ""),
                        text(request.phonePrefix(), ""),
                        text(request.phone(), ""),
                        text(request.preferredLanguage(), "en"),
                        normalizePhotoUrl(request.photoUrl()),
                        now,
                        now));
        String photoUrl = request.photoUrl() == null || request.photoUrl().isBlank()
                ? profile.getPhotoUrl()
                : normalizePhotoUrl(request.photoUrl());
        profile.update(
                text(request.firstName(), profile.getFirstName()),
                text(request.lastName(), profile.getLastName()),
                text(request.country(), profile.getCountry()),
                text(request.city(), profile.getCity()),
                text(request.phonePrefix(), profile.getPhonePrefix()),
                text(request.phone(), profile.getPhone()),
                text(request.preferredLanguage(), profile.getPreferredLanguage()),
                photoUrl,
                now);
        repository.save(profile);
        return toView(profile);
    }

    @Transactional
    public void deleteProfile(String email) {
        repository.deleteById(email);
    }

    private UserProfileView toView(UserProfileEntity profile) {
        return new UserProfileView(
                profile.getEmail(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getCountry(),
                profile.getCity(),
                profile.getPhonePrefix(),
                profile.getPhone(),
                profile.getPreferredLanguage(),
                profile.getPhotoUrl(),
                profile.getCreatedAt(),
                profile.getUpdatedAt());
    }

    private static String normalizePhotoUrl(String photoUrl) {
        return photoUrl == null || photoUrl.isBlank() ? null : photoUrl.trim();
    }

    private static String text(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value.trim();
    }
}
