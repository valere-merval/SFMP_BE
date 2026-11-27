package com.smartfan.mobility.transport.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TransportGeocodingService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TransportGeocodingService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(4))
                .build();
    }

    public Optional<Coordinate> resolve(String address) {
        if (address == null || address.isBlank()) {
            return Optional.empty();
        }
        try {
            String query = URLEncoder.encode(address.trim(), StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://nominatim.openstreetmap.org/search?format=jsonv2&limit=1&q=" + query))
                    .timeout(Duration.ofSeconds(5))
                    .header("Accept", "application/json")
                    .header("User-Agent", "SFMP-App/1.0 (transport-geocoding)")
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                return Optional.empty();
            }
            JsonNode root = objectMapper.readTree(response.body());
            if (!root.isArray() || root.isEmpty()) {
                return Optional.empty();
            }
            JsonNode first = root.get(0);
            if (first == null) {
                return Optional.empty();
            }
            double latitude = parseDouble(first.get("lat"));
            double longitude = parseDouble(first.get("lon"));
            if (!Double.isFinite(latitude) || !Double.isFinite(longitude)) {
                return Optional.empty();
            }
            return Optional.of(new Coordinate(latitude, longitude));
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

    private double parseDouble(JsonNode node) {
        if (node == null || node.isNull()) {
            return Double.NaN;
        }
        try {
            return Double.parseDouble(node.asText());
        } catch (Exception ignored) {
            return Double.NaN;
        }
    }

    public record Coordinate(double latitude, double longitude) {
    }
}
