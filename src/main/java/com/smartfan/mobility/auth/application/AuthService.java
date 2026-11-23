package com.smartfan.mobility.auth.application;

import com.smartfan.mobility.auth.api.AuthResponse;
import com.smartfan.mobility.auth.domain.AuthUser;
import com.smartfan.mobility.auth.domain.LoginRequest;
import com.smartfan.mobility.auth.domain.RegisterRequest;
import com.smartfan.mobility.auth.domain.ResetPasswordRequest;
import com.smartfan.mobility.auth.application.UserProfileService;
import com.smartfan.mobility.shared.auth.TokenService;
import java.util.List;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthDirectory authDirectory;
    private final TokenService tokenService;
    private final UserProfileService userProfileService;

    public AuthService(AuthDirectory authDirectory, TokenService tokenService, UserProfileService userProfileService) {
        this.authDirectory = authDirectory;
        this.tokenService = tokenService;
        this.userProfileService = userProfileService;
    }

    public AuthResponse login(String email, String password) {
        return issue(authDirectory.login(email, password));
    }

    public AuthResponse register(RegisterRequest request) {
        AuthUser user = authDirectory.register(request);
        userProfileService.saveProfile(user.email(), request);
        return issue(user);
    }

    public AuthResponse resetPassword(ResetPasswordRequest request) {
        return issue(authDirectory.resetPassword(request.email(), request.newPassword()));
    }

    public AuthUser currentUser() {
        return tokenService.currentUser().orElseThrow(() -> new AccessDeniedException("No authenticated user"));
    }

    public java.util.Optional<AuthUser> currentUserOptional() {
        return tokenService.currentUser();
    }

    public List<String> roles() {
        return currentUser().roles();
    }

    private AuthResponse issue(AuthUser user) {
        return new AuthResponse(tokenService.issue(user), user);
    }
}
