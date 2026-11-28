package com.smartfan.mobility.intelligence.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
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

    private static final CopyOnWriteArrayList<TravelIntentView> INTENTS = new CopyOnWriteArrayList<>();

    @GetMapping("/signals")
    public ApiResponse<TravelSignalPackView> signals(@RequestParam(value = "matchId", required = false) String matchId) {
        return ApiResponse.ok(buildSignals(matchId), "Travel signals ready");
    }

    @GetMapping("/forecasts")
    public ApiResponse<List<FixtureCatalogue.ForecastView>> forecasts(@RequestParam(value = "matchId", required = false) String matchId) {
        return ApiResponse.ok(buildSignals(matchId).forecasts(), "Travel forecasts ready");
    }

    @GetMapping("/demand")
    public ApiResponse<List<FixtureCatalogue.DemandPeakView>> demand(@RequestParam(value = "matchId", required = false) String matchId) {
        return ApiResponse.ok(buildSignals(matchId).demandPeaks(), "Demand peaks ready");
    }

    @GetMapping("/routes")
    public ApiResponse<List<FixtureCatalogue.RouteAdaptationView>> routes(@RequestParam(value = "matchId", required = false) String matchId) {
        return ApiResponse.ok(buildSignals(matchId).routeAdaptations(), "Route adaptations ready");
    }

    @GetMapping("/traffic")
    public ApiResponse<List<FixtureCatalogue.TrafficView>> traffic(@RequestParam(value = "matchId", required = false) String matchId) {
        return ApiResponse.ok(buildSignals(matchId).traffic(), "Traffic signals ready");
    }

    @GetMapping("/messages")
    public ApiResponse<List<FixtureCatalogue.ContextualMessageView>> messages(@RequestParam(value = "matchId", required = false) String matchId) {
        return ApiResponse.ok(buildSignals(matchId).contextualMessages(), "Contextual messages ready");
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
        TravelSignalPackView signalPack = buildSignals(request.matchId());
        String recommendation = buildRecommendation(mode, departureWindow, origin, accessibilityNeed, signalPack);
        String confidence = signalPack.confidence();
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

    private static TravelSignalPackView buildSignals(String matchId) {
        FixtureCatalogue.MatchView match = resolveMatch(matchId);
        String matchTitle = match == null ? "All matches" : match.homeTeam() + " vs " + match.awayTeam();
        String venue = match == null ? "Match venue" : match.venue();
        String city = match == null ? "Venue city" : match.city();
        String phase = getPhase(match);
        String crowdLevel = getCrowdLevel(match);
        String confidence = getConfidence(match);
        String timeToKickoff = getTimeToKickoff(match);
        String summary = buildSummary(matchTitle, venue, city, phase, crowdLevel, timeToKickoff);

        List<FixtureCatalogue.ForecastView> forecasts = List.of(
                new FixtureCatalogue.ForecastView(matchKey(match, "forecast-rail"), "Rail demand", crowdLevel, confidence, "Open rail guidance earlier for fans arriving by train and keep station walking active."),
                new FixtureCatalogue.ForecastView(matchKey(match, "forecast-metro"), "Metro pressure", phase, confidence, "Surface metro lines close to kickoff and show the walk from the station to the venue."),
                new FixtureCatalogue.ForecastView(matchKey(match, "forecast-bus-tram"), "Bus and tram flow", crowdLevel, confidence, "Promote bus and tram options for city-center arrivals and late collectors."),
                new FixtureCatalogue.ForecastView(matchKey(match, "forecast-last-mile"), "Last-mile demand", phase, confidence, "Keep bike, scooter and walking guidance visible for the final approach to the venue.")
        );

        List<FixtureCatalogue.DemandPeakView> demandPeaks = List.of(
                new FixtureCatalogue.DemandPeakView("T-150 to T-90", crowdLevel, "Push rail and metro options as soon as the match window opens."),
                new FixtureCatalogue.DemandPeakView("T-60 to T-20", "High", "Highlight tram and bus stops plus the walking path to the exact station."),
                new FixtureCatalogue.DemandPeakView("Final whistle to +45", "Peak", "Keep the pedestrian route and micromobility options active for the return flow.")
        );

        List<FixtureCatalogue.RouteAdaptationView> routeAdaptations = List.of(
                new FixtureCatalogue.RouteAdaptationView(venue + " north access", "Prefer pedestrian routing", "Fans are closer to the station entrance and foot traffic becomes dominant near kickoff."),
                new FixtureCatalogue.RouteAdaptationView(city + " transit corridor", "Show transit-first path", "The selected match is in a high-demand window and transit should be prioritized."),
                new FixtureCatalogue.RouteAdaptationView(venue + " last mile", "Surface the final walk", "The shortest walking path from the chosen stop to the venue should stay visible.")
        );

        List<FixtureCatalogue.TrafficView> traffic = List.of(
                new FixtureCatalogue.TrafficView(venue + " approach", crowdLevel, "Crowd density " + crowdLevel.toLowerCase(Locale.ROOT), "Use the quickest pedestrian corridor to the selected station or stop."),
                new FixtureCatalogue.TrafficView(city + " core", phase, "Crowd flow changes around match time", "Show transit lines and the walking connection in the transport card."),
                new FixtureCatalogue.TrafficView("Post-match exit", "Peak", "The exit wave increases after the final whistle", "Keep pedestrian navigation and voice guidance active.")
        );

        List<FixtureCatalogue.ContextualMessageView> messages = List.of(
                new FixtureCatalogue.ContextualMessageView("fans", "in-app", summary, timeToKickoff),
                new FixtureCatalogue.ContextualMessageView("drivers", "voice", "Guide travelers from the selected station or stop to " + venue + ".", "Before arrival"),
                new FixtureCatalogue.ContextualMessageView("operations", "map", "Highlight the line that best matches " + phase.toLowerCase(Locale.ROOT) + " flow.", "Live")
        );

        return new TravelSignalPackView(
                match == null ? "global" : match.id(),
                matchTitle,
                venue,
                city,
                phase,
                crowdLevel,
                confidence,
                timeToKickoff,
                summary,
                forecasts,
                demandPeaks,
                routeAdaptations,
                traffic,
                messages);
    }

    private static FixtureCatalogue.MatchView resolveMatch(String matchId) {
        if (matchId == null || matchId.isBlank()) {
            return FixtureCatalogue.matches().stream().findFirst().orElse(null);
        }
        return FixtureCatalogue.matches().stream()
                .filter(match -> matchId.equalsIgnoreCase(match.id()))
                .findFirst()
                .orElse(FixtureCatalogue.matches().stream().findFirst().orElse(null));
    }

    private static String getPhase(FixtureCatalogue.MatchView match) {
        long minutes = minutesUntilKickoff(match);
        if (minutes <= 0) {
            return "LIVE";
        }
        if (minutes <= 60) {
            return "Arrival window";
        }
        if (minutes <= 180) {
            return "Pre-match";
        }
        return "Early planning";
    }

    private static String getCrowdLevel(FixtureCatalogue.MatchView match) {
        long minutes = minutesUntilKickoff(match);
        if (minutes <= 0) {
            return "Peak";
        }
        if (minutes <= 45) {
            return "Very high";
        }
        if (minutes <= 120) {
            return "High";
        }
        if (minutes <= 240) {
            return "Rising";
        }
        return "Moderate";
    }

    private static String getConfidence(FixtureCatalogue.MatchView match) {
        if (match == null) {
            return "Medium";
        }
        if (match.stage() != null && match.stage().toLowerCase(Locale.ROOT).contains("final")) {
            return "High";
        }
        long minutes = minutesUntilKickoff(match);
        if (minutes <= 120) {
            return "High";
        }
        return "Medium";
    }

    private static String buildSummary(String matchTitle, String venue, String city, String phase, String crowdLevel, String timeToKickoff) {
        return matchTitle + " at " + venue + " in " + city + " is in the " + phase.toLowerCase(Locale.ROOT) + " phase with " + crowdLevel.toLowerCase(Locale.ROOT) + " crowd pressure. " + timeToKickoff + " remaining.";
    }

    private static String getTimeToKickoff(FixtureCatalogue.MatchView match) {
        long minutes = minutesUntilKickoff(match);
        if (minutes <= 0) {
            return "Live now";
        }
        long hours = minutes / 60;
        long remainingMinutes = minutes % 60;
        if (hours <= 0) {
            return remainingMinutes + " min to kickoff";
        }
        return hours + " h " + remainingMinutes + " min to kickoff";
    }

    private static long minutesUntilKickoff(FixtureCatalogue.MatchView match) {
        if (match == null || match.date() == null) {
            return 0L;
        }
        try {
            LocalTime time = LocalTime.parse(match.time().length() == 5 ? match.time() + ":00" : match.time());
            LocalDateTime kickoff = LocalDateTime.of(match.date(), time);
            return Duration.between(LocalDateTime.now(), kickoff).toMinutes();
        } catch (DateTimeParseException | NullPointerException ex) {
            return 0L;
        }
    }

    private static String matchKey(FixtureCatalogue.MatchView match, String fallback) {
        return match == null ? fallback : match.id() + ":" + fallback;
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    private static String buildRecommendation(String mode, String departureWindow, String origin, String accessibilityNeed, TravelSignalPackView signals) {
        String travelMode = mode.isBlank() ? "transit" : mode.toLowerCase(Locale.ROOT);
        String prefix = switch (travelMode) {
            case "train" -> "Rail looks strongest for this match";
            case "metro" -> "Metro is the fastest way to reach the venue";
            case "bus" -> "Bus access fits the current crowd flow";
            case "tram" -> "Tram access keeps the last mile simple";
            case "bike" -> "Bike parking is a good match for the current demand";
            case "scooter" -> "Scooter drop-off works well for the final approach";
            default -> "Transit and walking are the safest choices";
        };
        StringBuilder builder = new StringBuilder(prefix)
                .append(". ")
                .append(signals.summary());
        if (!departureWindow.isBlank()) {
            builder.append(" Departing ").append(departureWindow);
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

    public record TravelSignalPackView(
            String matchId,
            String matchTitle,
            String venue,
            String city,
            String phase,
            String crowdLevel,
            String confidence,
            String timeToKickoff,
            String summary,
            List<FixtureCatalogue.ForecastView> forecasts,
            List<FixtureCatalogue.DemandPeakView> demandPeaks,
            List<FixtureCatalogue.RouteAdaptationView> routeAdaptations,
            List<FixtureCatalogue.TrafficView> traffic,
            List<FixtureCatalogue.ContextualMessageView> contextualMessages) {
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
