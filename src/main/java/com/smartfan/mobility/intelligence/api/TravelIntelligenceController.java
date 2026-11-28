package com.smartfan.mobility.intelligence.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mobility-intelligence")
public class TravelIntelligenceController {

    private static final List<FixtureCatalogue.ForecastView> FORECASTS = List.of(
            new FixtureCatalogue.ForecastView("forecast-train-peak", "Rail demand spike", "High", "0.86", "Pre-stage station guidance 90 minutes before kickoff"),
            new FixtureCatalogue.ForecastView("forecast-city-core", "City-center departures", "Rising", "0.79", "Push metro and bus options from the city core"),
            new FixtureCatalogue.ForecastView("forecast-last-mile", "Last-mile mobility", "Very high", "0.84", "Reserve bike and scooter supply near the venue"),
            new FixtureCatalogue.ForecastView("forecast-post-match", "Post-match return", "Sustained", "0.81", "Keep walking navigation and station routes active after the final whistle")
    );

    private static final List<FixtureCatalogue.DemandPeakView> DEMAND = List.of(
            new FixtureCatalogue.DemandPeakView("T-120 to T-90", "High", "Open rail and metro guidance early"),
            new FixtureCatalogue.DemandPeakView("T-60 to T-30", "Very high", "Increase bus and tram visibility for late arrivals"),
            new FixtureCatalogue.DemandPeakView("Final whistle to +45", "Peak", "Prioritize walk-to-station routing and micromobility redistribution")
    );

    private static final CopyOnWriteArrayList<TravelIntentView> INTENTS = new CopyOnWriteArrayList<>();

    @GetMapping("/forecasts")
    public ApiResponse<List<FixtureCatalogue.ForecastView>> forecasts() {
        return ApiResponse.ok(FORECASTS, "Travel forecasts ready");
    }

    @GetMapping("/demand")
    public ApiResponse<List<FixtureCatalogue.DemandPeakView>> demand() {
        return ApiResponse.ok(DEMAND, "Demand peaks ready");
    }

    @GetMapping("/intents")
    public ApiResponse<List<TravelIntentView>> intents(@RequestParam(value = "matchId", required = false) String matchId) {
        List<TravelIntentView> filtered = INTENTS.stream()
                .filter(intent -> matchId == null || matchId.isBlank() || matchId.equalsIgnoreCase(intent.matchId()))
                .collect(Collectors.toList());
        return ApiResponse.ok(filtered, "Travel intents ready");
    }

    @PostMapping("/intents")
    public ApiResponse<TravelIntentView> createIntent(@RequestBody TravelIntentRequest request) {
        String mode = normalize(request.preferredMode());
        String origin = normalize(request.originArea());
        String departureWindow = normalize(request.departureWindow());
        String notes = normalize(request.notes());
        String accessibilityNeed = normalize(request.accessibilityNeed());
        String recommendation = buildRecommendation(mode, departureWindow, origin, accessibilityNeed);
        String confidence = (origin.isBlank() || departureWindow.isBlank()) ? "Medium" : "High";
        TravelIntentView saved = new TravelIntentView(
                UUID.randomUUID().toString(),
                normalize(request.matchId()),
                normalize(request.matchTitle()),
                origin,
                departureWindow,
                mode,
                normalize(request.partySize()),
                accessibilityNeed,
                recommendation,
                confidence,
                notes,
                LocalDateTime.now().toString());
        INTENTS.add(0, saved);
        return ApiResponse.ok(saved, "Travel intent stored");
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    private static String buildRecommendation(String mode, String departureWindow, String origin, String accessibilityNeed) {
        String travelMode = mode.isBlank() ? "transit" : mode.toLowerCase();
        String prefix = switch (travelMode) {
            case "train" -> "Prioritize rail and station walking guidance";
            case "metro" -> "Lead with metro access and pedestrian routing";
            case "bus" -> "Surface bus corridors and last-mile walking";
            case "tram" -> "Use tram access with clear stop-to-venue walking";
            case "bike" -> "Combine bike parking with walking guidance";
            case "scooter" -> "Combine scooter drop-off with pedestrian routing";
            default -> "Use the best transit and walking combination";
        };
        StringBuilder builder = new StringBuilder(prefix);
        if (!departureWindow.isBlank()) {
            builder.append(" for ").append(departureWindow);
        }
        if (!origin.isBlank()) {
            builder.append(" from ").append(origin);
        }
        if (!accessibilityNeed.isBlank()) {
            builder.append(" and keep accessibility guidance active");
        }
        builder.append('.');
        return builder.toString();
    }

    public record TravelIntentRequest(
            String matchId,
            String matchTitle,
            String originArea,
            String departureWindow,
            String preferredMode,
            String partySize,
            String accessibilityNeed,
            String notes) {
    }

    public record TravelIntentView(
            String id,
            String matchId,
            String matchTitle,
            String originArea,
            String departureWindow,
            String preferredMode,
            String partySize,
            String accessibilityNeed,
            String recommendation,
            String confidence,
            String notes,
            String createdAt) {
    }
}
