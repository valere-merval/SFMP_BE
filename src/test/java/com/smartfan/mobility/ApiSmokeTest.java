package com.smartfan.mobility;

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
class ApiSmokeTest {

    @Value("${local.server.port}")
    int port;

    private final HttpClient client = HttpClient.newHttpClient();

    @Test
    void protectedEndpointsRequireLoginAndWorkWithToken() throws IOException, InterruptedException {
        HttpResponse<String> unauthorized = get("/api/v1/events", null);
        assertTrue(unauthorized.statusCode() == 403 || unauthorized.statusCode() == 401);

        String token = login("fan@sfmp.local", "fan123");
        HttpResponse<String> events = get("/api/v1/events", token);
        assertTrue(events.statusCode() == 200);
        assertTrue(events.body().contains("UEFA Champions League Final"));
    }

    @Test
    void roadmapAndIntelligenceEndpointsAreAvailable() throws IOException, InterruptedException {
        String token = login("ops@sfmp.local", "ops123");
        HttpResponse<String> roadmap = get("/api/v1/platform/roadmap", token);
        HttpResponse<String> heatmaps = get("/api/v1/crowd/heatmaps", token);
        HttpResponse<String> traffic = get("/api/v1/traffic/conditions", token);
        HttpResponse<String> insights = get("/api/v1/intelligence/insights", token);

        assertTrue(roadmap.statusCode() == 200);
        assertTrue(roadmap.body().contains("Version 3"));
        assertTrue(heatmaps.statusCode() == 200);
        assertTrue(heatmaps.body().contains("Gate 12"));
        assertTrue(traffic.statusCode() == 200);
        assertTrue(traffic.body().contains("Airport Corridor"));
        assertTrue(insights.statusCode() == 200);
        assertTrue(insights.body().contains("PeakNet v2"));
    }

    @Test
    void loginReturnsAccessToken() throws IOException, InterruptedException {
        HttpResponse<String> response = post("/api/v1/auth/login", "{\"username\":\"admin@sfmp.local\",\"password\":\"admin123\"}", null);
        assertTrue(response.statusCode() == 200);
        assertTrue(response.body().contains("accessToken"));
    }

    private String login(String username, String password) throws IOException, InterruptedException {
        HttpResponse<String> response = post("/api/v1/auth/login", String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password), null);
        Matcher matcher = Pattern.compile("\\\"accessToken\\\"\\s*:\\s*\\\"([^\\\"]+)\\\"").matcher(response.body());
        if (!matcher.find()) {
            throw new IllegalStateException("No token in response: " + response.body());
        }
        return matcher.group(1);
    }

    private HttpResponse<String> get(String path, String bearerToken) throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET();
        if (bearerToken != null) {
            builder.header("Authorization", "Bearer " + bearerToken);
        }
        return client.send(builder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
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
