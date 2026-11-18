package com.smartfan.mobility.intelligence.api;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntelligenceControllerTest {

    @Value("${local.server.port}")
    int port;

    private final HttpClient client = HttpClient.newHttpClient();

    @Test
    void analyzeEndpointReturnsAiOrFallbackAnalysis() throws IOException, InterruptedException {
        String token = login("admin@sfmp.local", "admin123");
        HttpResponse<String> response = post("/api/v1/intelligence/analyze", "{\"focus\":\"peak demand\",\"context\":\"Stadium exit after final whistle\"}", token);
        assertTrue(response.statusCode() == 200);
        assertTrue(response.body().contains("analysis"));
        assertTrue(response.body().contains("recommendations"));
    }

    private String login(String username, String password) throws IOException, InterruptedException {
        HttpResponse<String> response = post("/api/v1/auth/login", String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password), null);
        Matcher matcher = Pattern.compile("\\\"accessToken\\\"\\s*:\\s*\\\"([^\\\"]+)\\\"").matcher(response.body());
        if (!matcher.find()) {
            throw new IllegalStateException("No token in response: " + response.body());
        }
        return matcher.group(1);
    }

    private HttpResponse<String> post(String path, String body, String bearerToken) throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8));
        if (bearerToken != null) {
            builder.header("Authorization", "Bearer " + bearerToken);
        }
        return client.send(builder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    }
}
