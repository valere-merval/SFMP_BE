package com.smartfan.mobility.auth.application;

import com.smartfan.mobility.auth.api.AuthResponse;
import com.smartfan.mobility.auth.domain.AuthUser;
import com.smartfan.mobility.auth.domain.LoginRequest;
import com.smartfan.mobility.shared.auth.TokenService;
import java.util.List;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TokenService tokenService;

    public AuthService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public AuthResponse login(String username, String password) {
        AuthUser user = switch (normalize(username)) {
            case "fan@sfmp.local" -> new AuthUser("fan-1", "Amina Diallo", "FAN", "Madrid", List.of("FAN"));
            case "ops@sfmp.local" -> new AuthUser("ops-1", "Hassan Karim", "OPERATIONS", "Metro de Madrid", List.of("FAN", "OPS"));
            case "admin@sfmp.local" -> new AuthUser("admin-1", "Valeria Gomez", "ADMIN", "SFMP", List.of("FAN", "OPS", "ADMIN"));
            default -> throw new AccessDeniedException("Invalid credentials");
        };
        if (!isPasswordValid(username, password)) {
            throw new AccessDeniedException("Invalid credentials");
        }
        return new AuthResponse(tokenService.issue(user), user);
    }

    public AuthUser currentUser() {
        return tokenService.currentUser().orElseThrow(() -> new AccessDeniedException("No authenticated user"));
    }

    public List<String> roles() {
        return currentUser().roles();
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }

    private static boolean isPasswordValid(String username, String password) {
        return switch (normalize(username)) {
            case "fan@sfmp.local" -> "fan123".equals(password);
            case "ops@sfmp.local" -> "ops123".equals(password);
            case "admin@sfmp.local" -> "admin123".equals(password);
            default -> false;
        };
    }
}
