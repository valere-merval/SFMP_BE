package com.smartfan.mobility.auth.domain;

public record ResetPasswordRequest(String email, String newPassword) {
}
