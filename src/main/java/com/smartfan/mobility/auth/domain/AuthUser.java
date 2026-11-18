package com.smartfan.mobility.auth.domain;

import java.util.List;

public record AuthUser(String id, String fullName, String role, String organization, List<String> roles) {
}
