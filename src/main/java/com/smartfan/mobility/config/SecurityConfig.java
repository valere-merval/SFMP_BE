package com.smartfan.mobility.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.smartfan.mobility.auth.security.BearerTokenFilter;
import com.smartfan.mobility.shared.auth.TokenService;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Value("${app.cors.allowed-origins:http://localhost:5173,http://localhost:5174,http://localhost:5175,http://127.0.0.1:5173,http://127.0.0.1:5174,http://127.0.0.1:5175,https://sfmp.nearhall.com}")
    private List<String> allowedOrigins;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, TokenService tokenService) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/error", "/actuator/**", "/api/v1/auth/**", "/api/v1/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/**").authenticated()
                        .requestMatchers("/api/v1/**").authenticated()
                        .anyRequest().permitAll())
                .addFilterBefore(new BearerTokenFilter(tokenService), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(allowedOrigins);
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
