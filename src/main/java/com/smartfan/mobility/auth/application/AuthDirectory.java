package com.smartfan.mobility.auth.application;

import com.smartfan.mobility.auth.domain.AuthAccountEntity;
import com.smartfan.mobility.auth.domain.AuthAccountRepository;
import com.smartfan.mobility.auth.domain.AuthUser;
import com.smartfan.mobility.auth.domain.RegisterRequest;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthDirectory {

    private final AuthAccountRepository authAccountRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final AtomicLong sequence = new AtomicLong(1);

    public AuthDirectory(AuthAccountRepository authAccountRepository) {
        this.authAccountRepository = authAccountRepository;
        seedIfNeeded();
    }

    @Transactional(readOnly = true)
    public AuthUser login(String email, String password) {
        String normalized = normalize(email);
        AuthAccountEntity account = authAccountRepository.findByEmailIgnoreCase(normalized)
            .orElseThrow(() -> new AccessDeniedException("Invalid credentials"));
        if (!passwordEncoder.matches(password == null ? "" : password, account.getPasswordHash())) {
            throw new AccessDeniedException("Invalid credentials");
        }
        return account.toUser();
    }

    @Transactional
    public AuthUser register(RegisterRequest request) {
        String email = normalize(request.email());
        if (email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (authAccountRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException("Email already registered");
        }
        String role = normalizeAccountType(request.accountType());
        String fullName = buildFullName(request.firstName(), request.lastName(), email);
        List<String> roles = switch (role) {
            case "OPS" -> List.of("FAN", "OPS");
            case "ADMIN" -> List.of("FAN", "OPS", "ADMIN");
            default -> List.of("FAN");
        };
        AuthAccountEntity entity = new AuthAccountEntity(nextId(), email, fullName, role, fallbackOrganization(request), roles, passwordEncoder.encode(request.password() == null ? "" : request.password()));
        return authAccountRepository.save(entity).toUser();
    }

    @Transactional
    public AuthUser resetPassword(String email, String newPassword) {
        String normalized = normalize(email);
        AuthAccountEntity current = authAccountRepository.findByEmailIgnoreCase(normalized)
            .orElseThrow(() -> new IllegalArgumentException("Unknown email"));
        current.setPasswordHash(passwordEncoder.encode(newPassword == null ? "" : newPassword));
        return authAccountRepository.save(current).toUser();
    }

    @Transactional
    protected void seedIfNeeded() {
        seed("fan-1", "fan@sfmp.local", "Amina Diallo", "FAN", "Madrid", List.of("FAN"), "fan123");
        seed("ops-1", "ops@sfmp.local", "Hassan Karim", "OPERATIONS", "Metro de Madrid", List.of("FAN", "OPS"), "ops123");
        seed("admin-1", "admin@sfmp.local", "Valeria Gomez", "ADMIN", "SFMP", List.of("FAN", "OPS", "ADMIN"), "admin123");
        seed("admin-2", "v.youbi@merval-digitale.com", "Valere Youbi", "ADMIN", "Merval Digitale", List.of("FAN", "OPS", "ADMIN"), "Royaume1991");
    }

    private void seed(String id, String email, String fullName, String role, String organization, List<String> roles, String password) {
        String normalized = normalize(email);
        if (authAccountRepository.existsByEmailIgnoreCase(normalized)) {
            return;
        }
        authAccountRepository.save(new AuthAccountEntity(id, normalized, fullName, role, organization, roles, passwordEncoder.encode(password)));
        sequence.updateAndGet(current -> Math.max(current, extractSequence(id) + 1));
    }

    private String nextId() {
        return "user-" + sequence.getAndIncrement();
    }

    private static long extractSequence(String id) {
        if (id == null) {
            return 1L;
        }
        String digits = id.replaceAll("[^0-9]", "");
        if (digits.isBlank()) {
            return 1L;
        }
        try {
            return Long.parseLong(digits);
        } catch (NumberFormatException exception) {
            return 1L;
        }
    }

    private static String fallbackOrganization(RegisterRequest request) {
        if (request.country() != null && !request.country().isBlank()) {
            return request.country().trim();
        }
        if (request.city() != null && !request.city().isBlank()) {
            return request.city().trim();
        }
        return "SFMP";
    }

    private static String buildFullName(String firstName, String lastName, String fallback) {
        String fullName = ((firstName == null ? "" : firstName.trim()) + " " + (lastName == null ? "" : lastName.trim())).trim();
        return fullName.isBlank() ? fallback : fullName;
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }

    private static String normalizeAccountType(String value) {
        if (value == null || value.isBlank()) {
            return "FAN";
        }
        String normalized = value.trim().toUpperCase(Locale.ROOT);
        return switch (normalized) {
            case "OPERATOR", "OPS", "OPERATIONS" -> "OPS";
            case "ADMIN" -> "ADMIN";
            default -> "FAN";
        };
    }
}
