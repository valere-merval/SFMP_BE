package com.smartfan.mobility.auth.api;

import com.smartfan.mobility.auth.domain.AuthUser;

public record RegisterResponse(String accessToken, AuthUser user, String message) {
}
