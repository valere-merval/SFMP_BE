package com.smartfan.mobility.auth.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

public record LoginRequest(@JsonAlias({"username"}) String email, String password) {
}
