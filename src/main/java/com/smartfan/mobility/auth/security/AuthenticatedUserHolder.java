package com.smartfan.mobility.auth.security;

import com.smartfan.mobility.auth.domain.AuthUser;
import com.smartfan.mobility.shared.auth.TokenService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

final class AuthenticatedUserHolder {

    private AuthenticatedUserHolder() {
    }

    static void authenticate(TokenService tokenService, String token) {
        AuthUser user = tokenService.parse(token).orElseThrow(() -> new AccessDeniedException("Invalid or expired token"));
        var authorities = user.roles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
        var authentication = new UsernamePasswordAuthenticationToken(user, token, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        tokenService.setCurrentUser(user);
    }
}
