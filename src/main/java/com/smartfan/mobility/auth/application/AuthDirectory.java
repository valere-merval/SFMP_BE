package com.smartfan.mobility.auth.application;

import com.smartfan.mobility.auth.domain.AuthUser;
import com.smartfan.mobility.auth.domain.RegisterRequest;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthDirectory {

    private final Map<String, StoredAccount> accounts = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(4);
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthDirectory() {
        seed("fan-1", "fan@sfmp.local", "Amina Diallo", "FAN", "Madrid", List.of("FAN"), "fan123");
        seed("ops-1", "ops@sfmp.local", "Hassan Karim", "OPERATIONS", "Metro de Madrid", List.of("FAN", "OPS"), "ops123");
        seed("admin-1", "admin@sfmp.local", "Valeria Gomez", "ADMIN", "SFMP", List.of("FAN", "OPS", "ADMIN"), "admin123");
    }

    public AuthUser login(String email, String password) {
        StoredAccount account = accounts.get(normalize(email));
        if (account == null || !passwordEncoder.matches(password == null ? "" : password, account.passwordHash())) {
            throw new AccessDeniedException("Invalid credentials");
        }
        return account.user();
    }

    public AuthUser register(RegisterRequest request) {
        String email = normalize(request.email());
        if (email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (accounts.containsKey(email)) {
            throw new IllegalArgumentException("Email already registered");
        }
        String role = normalizeAccountType(request.accountType());
        String fullName = buildFullName(request.firstName(), request.lastName(), email);
        List<String> roles = switch (role) {
            case "OPS" -> List.of("FAN", "OPS");
            case "ADMIN" -> List.of("FAN", "OPS", "ADMIN");
            default -> List.of("FAN");
        };
        AuthUser user = new AuthUser(nextId(), email, fullName, role, fallbackOrganization(request), roles);
        accounts.put(email, new StoredAccount(user, passwordEncoder.encode(request.password() == null ? "" : request.password())));
        return user;
    }

    public AuthUser resetPassword(String email, String newPassword) {
        String normalized = normalize(email);
        StoredAccount current = accounts.get(normalized);
        if (current == null) {
            throw new IllegalArgumentException("Unknown email");
        }
        accounts.put(normalized, current.withPassword(passwordEncoder.encode(newPassword == null ? "" : newPassword)));
        return current.user();
    }

    private void seed(String id, String email, String fullName, String role, String organization, List<String> roles, String password) {
        AuthUser user = new AuthUser(id, normalize(email), fullName, role, organization, roles);
        accounts.put(normalize(email), new StoredAccount(user, passwordEncoder.encode(password)));
    }

    private String nextId() {
        return "user-" + sequence.getAndIncrement();
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

    private record StoredAccount(AuthUser user, String passwordHash) {
        StoredAccount withPassword(String newHash) {
            return new StoredAccount(user, newHash);
        }
    }
}
