package com.smartfan.mobility.auth.api;

import com.smartfan.mobility.auth.application.AuthService;
import com.smartfan.mobility.auth.domain.AuthUser;
import com.smartfan.mobility.auth.domain.LoginRequest;
import com.smartfan.mobility.auth.domain.RegisterRequest;
import com.smartfan.mobility.auth.domain.ResetPasswordRequest;
import com.smartfan.mobility.shared.api.ApiResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request.email(), request.password());
        return ApiResponse.ok(response, "Login successful");
    }

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ApiResponse.ok(response, "Account created");
    }

    @PostMapping("/reset-password")
    public ApiResponse<AuthResponse> resetPassword(@RequestBody ResetPasswordRequest request) {
        AuthResponse response = authService.resetPassword(request);
        return ApiResponse.ok(response, "Password updated");
    }

    @GetMapping("/me")
    public ApiResponse<AuthUser> me() {
        return ApiResponse.ok(authService.currentUserOptional().orElse(null), "Current user");
    }

    @GetMapping("/roles")
    public ApiResponse<List<String>> roles() {
        return ApiResponse.ok(authService.roles(), "Role list");
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/logout")
    public void logout() {
        // stateless tokens expire naturally; endpoint exists for symmetry.
    }
}
