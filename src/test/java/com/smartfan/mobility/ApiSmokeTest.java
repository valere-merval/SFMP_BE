package com.smartfan.mobility;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiSmokeTest {

    @Value("${local.server.port}")
    int port;

    private final HttpClient client = HttpClient.newHttpClient();

    @Test
    void platformEndpointWorks() throws IOException, InterruptedException {
        HttpResponse<String> response = get("/api/v1/platform");
        assertTrue(response.statusCode() == 200);
        assertTrue(response.body().contains("\"success\":true"));
    }

    @Test
    void eventsEndpointWorks() throws IOException, InterruptedException {
        HttpResponse<String> response = get("/api/v1/events");
        assertTrue(response.statusCode() == 200);
        assertTrue(response.body().contains("UEFA Champions League Final"));
    }

    private HttpResponse<String> get(String path) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
