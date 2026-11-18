package com.smartfan.mobility.auth.api;

import com.smartfan.mobility.auth.domain.AuthUser;

public record AuthResponse(String accessToken, AuthUser user) {
}
