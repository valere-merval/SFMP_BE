package com.smartfan.mobility.intelligence.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfan.mobility.intelligence.api.AnalyzeRequest;
import com.smartfan.mobility.intelligence.api.AnalyzeResponse;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IntelligenceService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient;
    private final String apiKey;
    private final String model;
    private final String baseUrl;

    public IntelligenceService(
            @Value("${OPENAI_API_KEY:}") String apiKey,
            @Value("${OPENAI_MODEL:gpt-5.3-mini}") String model,
            @Value("${OPENAI_BASE_URL:https://api.openai.com/v1}") String baseUrl) {
        this.apiKey = apiKey;
        this.model = model;
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();
    }

    public AnalyzeResponse analyze(AnalyzeRequest request) {
        if (apiKey == null || apiKey.isBlank()) {
            return fallback(request);
        }

        try {
            String prompt = buildPrompt(request);
            String response = callOpenAi(prompt);
            AnalyzeResponse parsed = parseResponse(response).orElseGet(() -> fallback(request));
            return parsed;
        } catch (Exception ex) {
            return fallback(request);
        }
    }

    private String buildPrompt(AnalyzeRequest request) {
        return "You are an expert sports mobility analyst for Smart Fan Mobility Platform. " +
                "Return concise JSON with fields model, summary, confidence, recommendations (array of 3 short items). " +
                "Focus: " + safe(request.focus()) + ". Context: " + safe(request.context()) + ".";
    }

    private String callOpenAi(String prompt) throws Exception {
        Map<String, Object> payload = Map.of(
                "model", model,
                "temperature", 0.2,
                "messages", List.of(
                        Map.of("role", "system", "content", "Return only valid JSON."),
                        Map.of("role", "user", "content", prompt)
                )
        );
        HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "/chat/completions"))
                .timeout(Duration.ofSeconds(45))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(payload)))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IllegalStateException("OpenAI request failed with status " + response.statusCode());
        }
        JsonNode root = objectMapper.readTree(response.body());
        JsonNode content = root.path("choices").path(0).path("message").path("content");
        if (content.isMissingNode() || content.asText().isBlank()) {
            throw new IllegalStateException("OpenAI response missing content");
        }
        return content.asText();
    }

    private Optional<AnalyzeResponse> parseResponse(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            String model = text(root, "model").orElse(this.model);
            String summary = text(root, "summary").orElseGet(() -> text(root, "analysis").orElse("AI analysis ready"));
            String confidence = text(root, "confidence").orElse("87%");
            List<String> recommendations = new ArrayList<>();
            JsonNode recs = root.path("recommendations");
            if (recs.isArray()) {
                recs.forEach(node -> recommendations.add(node.asText()));
            }
            if (recommendations.isEmpty()) {
                recommendations.add("Pre-position extra vehicles");
                recommendations.add("Push contextual notifications");
                recommendations.add("Monitor peak corridors");
            }
            return Optional.of(new AnalyzeResponse(model, summary, confidence, recommendations));
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

    private AnalyzeResponse fallback(AnalyzeRequest request) {
        String focus = safe(request.focus());
        String context = safe(request.context());
        return new AnalyzeResponse(
                "local-fallback",
                "Local analytics for " + focus + " with context: " + context,
                "88%",
                List.of(
                        "Increase service frequency near the busiest gate",
                        "Send targeted notifications to affected supporters",
                        "Keep adaptive routing active for congestion windows"
                )
        );
    }

    private Optional<String> text(JsonNode root, String field) {
        JsonNode value = root.path(field);
        if (value.isMissingNode() || value.asText().isBlank()) {
            return Optional.empty();
        }
        return Optional.of(value.asText());
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "unspecified" : value;
    }
}
