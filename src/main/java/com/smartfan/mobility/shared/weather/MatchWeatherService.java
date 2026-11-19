package com.smartfan.mobility.shared.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MatchWeatherService {

    public record MatchWeatherSnapshot(String weather, String temperature) {}

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient;
    private final String apiKey;
    private final String model;
    private final String baseUrl;

    public MatchWeatherService(
            @Value("${OPENAI_API_KEY:}") String apiKey,
            @Value("${OPENAI_MODEL:gpt-5.3-mini}") String model,
            @Value("${OPENAI_BASE_URL:https://api.openai.com/v1}") String baseUrl) {
        this.apiKey = apiKey;
        this.model = model;
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();
    }

    public MatchWeatherSnapshot enrich(String city, String country, String venue, LocalDate date, String time) {
        if (apiKey == null || apiKey.isBlank()) {
            return fallback(city, country, venue, time);
        }

        try {
            String prompt = "Return only JSON with weather and temperature for a football matchday. " +
                    "Context: city=" + safe(city) + ", country=" + safe(country) + ", venue=" + safe(venue) + ", date=" + date + ", time=" + safe(time) + ".";
            HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "/chat/completions"))
                    .timeout(Duration.ofSeconds(45))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(Map.of(
                            "model", model,
                            "temperature", 0.2,
                            "messages", List.of(
                                    Map.of("role", "system", "content", "Return only valid JSON."),
                                    Map.of("role", "user", "content", prompt)
                            )
                    ))))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                JsonNode root = objectMapper.readTree(response.body());
                JsonNode content = root.path("choices").path(0).path("message").path("content");
                if (!content.isMissingNode() && !content.asText().isBlank()) {
                    JsonNode parsed = objectMapper.readTree(content.asText());
                    String weather = parsed.path("weather").asText().isBlank() ? fallback(city, country, venue, time).weather() : parsed.path("weather").asText();
                    String temperature = parsed.path("temperature").asText().isBlank() ? fallback(city, country, venue, time).temperature() : parsed.path("temperature").asText();
                    return new MatchWeatherSnapshot(weather, temperature);
                }
            }
        } catch (Exception ignored) {
        }

        return fallback(city, country, venue, time);
    }

    private MatchWeatherSnapshot fallback(String city, String country, String venue, String time) {
        int seed = Math.abs((safe(city) + safe(country) + safe(venue) + safe(time)).hashCode());
        String[] weatherPool = {"Clear sky", "Partly cloudy", "Light breeze", "Dry evening", "Overcast"};
        int temp = 16 + (seed % 12);
        return new MatchWeatherSnapshot(weatherPool[seed % weatherPool.length], temp + "°C");
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "unspecified" : value;
    }
}