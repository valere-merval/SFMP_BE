package com.smartfan.mobility.shared.auth;

import com.smartfan.mobility.auth.domain.AuthUser;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenService {

    private final String secret;
    private final ThreadLocal<AuthUser> currentUser = new ThreadLocal<>();

    public TokenService(@Value("${sfmp.auth.secret:sfmp-dev-secret-change-me}") String secret) {
        this.secret = secret;
    }

    public String issue(AuthUser user) {
        long exp = Instant.now().plusSeconds(60 * 60 * 12).getEpochSecond();
        String payload = String.join("|", user.id(), user.email(), user.fullName(), user.role(), user.organization(), String.join(",", user.roles()), Long.toString(exp));
        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        String signature = sign(encoded);
        return encoded + "." + signature;
    }

    public Optional<AuthUser> parse(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 2) return Optional.empty();
            if (!sign(parts[0]).equals(parts[1])) return Optional.empty();
            String payload = new String(Base64.getUrlDecoder().decode(parts[0]), StandardCharsets.UTF_8);
            String[] values = payload.split("\\|", -1);
            if (values.length != 7) return Optional.empty();
            long exp = Long.parseLong(values[6]);
            if (Instant.now().getEpochSecond() > exp) return Optional.empty();
            List<String> roles = values[5].isBlank() ? List.of() : List.of(values[5].split(","));
            return Optional.of(new AuthUser(values[0], values[1], values[2], values[3], values[4], roles));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public void setCurrentUser(AuthUser user) {
        currentUser.set(user);
    }

    public Optional<AuthUser> currentUser() {
        return Optional.ofNullable(currentUser.get());
    }

    public void clear() {
        currentUser.remove();
    }

    private String sign(String value) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] raw = mac.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(raw);
        } catch (Exception ex) {
            throw new IllegalStateException("Token signing failed", ex);
        }
    }
}
