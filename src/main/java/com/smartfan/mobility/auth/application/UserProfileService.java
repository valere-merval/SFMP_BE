package com.smartfan.mobility.auth.application;

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
        profile.update(
                text(request.firstName(), email),
                text(request.lastName(), email),
                text(request.country(), ""),
                text(request.city(), ""),
                text(request.phonePrefix(), ""),
                text(request.phone(), ""),
                text(request.preferredLanguage(), "en"),
                normalizePhotoUrl(request.photoUrl()),
                now);
        repository.save(profile);
    }

    private static String normalizePhotoUrl(String photoUrl) {
        return photoUrl == null || photoUrl.isBlank() ? null : photoUrl.trim();
    }

    private static String text(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value.trim();
    }
}
