package com.smartfan.mobility.auth.api;

import com.smartfan.mobility.auth.application.AuthService;
import com.smartfan.mobility.auth.application.UserProfileService;
import com.smartfan.mobility.shared.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-profiles")
public class UserProfileController {

    private final AuthService authService;
    private final UserProfileService userProfileService;

    public UserProfileController(AuthService authService, UserProfileService userProfileService) {
        this.authService = authService;
        this.userProfileService = userProfileService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileView>> me() {
        return ResponseEntity.ok(ApiResponse.ok(userProfileService.me(authService.currentUser().email()), "OK"));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileView>> updateMe(@RequestBody UserProfileRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(userProfileService.upsert(authService.currentUser().email(), request), "OK"));
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> deleteMe() {
        userProfileService.deleteProfile(authService.currentUser().email());
        return ResponseEntity.ok(ApiResponse.ok(null, "OK"));
    }

    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse<UserProfileView>> byEmail(@PathVariable String email) {
        return ResponseEntity.ok(ApiResponse.ok(userProfileService.me(email), "OK"));
    }
}
