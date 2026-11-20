package com.smartfan.mobility.shared.catalogue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class FixtureCatalogue {

    private static final CatalogueSnapshot SNAPSHOT = loadSnapshot();
    private static final CopyOnWriteArrayList<EventCatalogView> EVENT_CATALOGS = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<MatchView> MATCHES = new CopyOnWriteArrayList<>();

    private FixtureCatalogue() {
    }

    public record EventView(String id, String name, String city, String venue, LocalDate date, String status, int expectedAttendance) {
    }

    public record EventCatalogView(String id, String title, String description, String imageUrl, String sport, String country, String badge, int eventCount) {
    }

    public record MatchView(
            String id,
            String catalogId,
            String homeTeam,
            String awayTeam,
            String homeFlag,
            String awayFlag,
            String city,
            String country,
            String venue,
            String stage,
            LocalDate date,
            String time,
            String status,
            String gate,
            String sector,
            String countdown,
            String weather,
            String temperature,
            String transitSummary,
            boolean ticketAvailable,
            String ticketPrice,
            Integer ticketsTotal,
            Integer ticketsRemaining,
            String imageUrl) {
    }

    public record VenueView(String id, String name, String city, String zoneCount, String accessPoints, String capacity) {
    }

    public record MobilityOptionView(String id, String mode, String duration, String price, String status, String highlight) {
    }

    public record BookingView(String id, String fanName, String eventName, String option, String status, String departureTime) {
    }

    public record DriverView(String id, String name, String vehicle, String zone, String status) {
    }

    public record PartnerView(String id, String name, String service, String coverage, String status) {
    }

    public record MetricView(String id, String label, String value, String trend) {
    }

    public record ForecastView(String id, String label, String value, String confidence, String action) {
    }

    public record NotificationView(String id, String title, String channel, String status, LocalDateTime sentAt) {
    }

    public record AnalyticsView(String id, String label, String value, String unit, String trend) {
    }

    public record RoadmapView(String version, String title, List<String> features) {
    }

    public record HeatmapView(String zone, String intensity, String trend, String recommendation) {
    }

    public record DemandPeakView(String window, String level, String action) {
    }

    public record RouteAdaptationView(String corridor, String change, String reason) {
    }

    public record ContextualMessageView(String audience, String channel, String message, String timing) {
    }

    public record TrafficView(String corridor, String status, String impact, String recommendation) {
    }

    public record ConsentView(String source, String status, String lastUpdated) {
    }

    public record InsightView(String model, String confidence, String insight, String action) {
    }

    public record AuditView(String id, String action, String actor, String resource, LocalDateTime at) {
    }

    public record AssetView(String id, String name, String type, String status, String url) {
    }

    public record OrganizationView(String id, String name, String type, String status) {
    }

    public record UserView(String id, String fullName, String role, String organization, String status) {
    }

    public record IntegrationView(String id, String name, String status, String endpoint) {
    }

    public record TransportProviderView(String id, String matchId, String matchTitle, String providerType, String displayName, String vehicleLabel, String zone, String status, boolean active, String city, String country) {
    }

    public record CatalogueSnapshot(
            List<EventView> events,
            List<EventCatalogView> eventCatalogs,
            List<MatchView> matches,
            List<VenueView> venues,
            List<MobilityOptionView> mobilityOptions,
            List<BookingView> bookings,
            List<DriverView> drivers,
            List<PartnerView> partners,
            List<MetricView> crowdMetrics,
            List<ForecastView> forecasts,
            List<NotificationView> notifications,
            List<AnalyticsView> analytics,
            List<RoadmapView> roadmap,
            List<HeatmapView> heatmaps,
            List<DemandPeakView> demandPeaks,
            List<RouteAdaptationView> routeAdaptations,
            List<ContextualMessageView> contextualMessages,
            List<AnalyticsView> advancedAnalytics,
            List<TrafficView> traffic,
            List<ConsentView> gpsConsents,
            List<InsightView> mlInsights,
            List<AuditView> auditEntries,
            List<AssetView> mediaAssets,
            List<OrganizationView> organizations,
            List<UserView> users,
            List<IntegrationView> integrations,
            List<MetricView> operationsSummary,
            List<MetricView> adminSummary,
            List<TransportProviderView> transportProviders) {
    }

    public static CatalogueSnapshot snapshot() {
        return new CatalogueSnapshot(
                events(),
                eventCatalogs(),
                matches(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of());
    }

    public static List<EventView> events() {
        return MATCHES.stream()
                .map(match -> new EventView(match.id(), match.homeTeam() + " vs " + match.awayTeam(), match.city(), match.venue(), match.date(), match.status(), 0))
                .toList();
    }

    public static List<EventCatalogView> eventCatalogs() {
        return List.copyOf(EVENT_CATALOGS);
    }

    public static void addEventCatalog(EventCatalogView catalog) {
        EVENT_CATALOGS.add(catalog);
    }

    public static List<MatchView> matches() {
        return List.copyOf(MATCHES);
    }

    public static void addMatch(MatchView match) {
        MATCHES.add(match);
    }

    public static List<MatchView> matchesForCatalog(String catalogId) {
        if (catalogId == null || catalogId.isBlank()) {
            return matches();
        }
        return matches().stream().filter(match -> catalogId.equalsIgnoreCase(match.catalogId())).toList();
    }

    public static List<VenueView> venues() {
        return SNAPSHOT.venues();
    }

    public static List<MobilityOptionView> mobilityOptions() {
        return SNAPSHOT.mobilityOptions();
    }

    public static List<BookingView> bookings() {
        return SNAPSHOT.bookings();
    }

    public static List<DriverView> drivers() {
        return SNAPSHOT.drivers();
    }

    public static List<PartnerView> partners() {
        return SNAPSHOT.partners();
    }

    public static List<MetricView> crowdMetrics() {
        return SNAPSHOT.crowdMetrics();
    }

    public static List<ForecastView> forecasts() {
        return SNAPSHOT.forecasts();
    }

    public static List<NotificationView> notifications() {
        return SNAPSHOT.notifications();
    }

    public static List<AnalyticsView> analytics() {
        return SNAPSHOT.analytics();
    }

    public static List<RoadmapView> roadmap() {
        return SNAPSHOT.roadmap();
    }

    public static List<HeatmapView> heatmaps() {
        return SNAPSHOT.heatmaps();
    }

    public static List<DemandPeakView> demandPeaks() {
        return SNAPSHOT.demandPeaks();
    }

    public static List<RouteAdaptationView> routeAdaptations() {
        return SNAPSHOT.routeAdaptations();
    }

    public static List<ContextualMessageView> contextualMessages() {
        return SNAPSHOT.contextualMessages();
    }

    public static List<AnalyticsView> advancedAnalytics() {
        return SNAPSHOT.advancedAnalytics();
    }

    public static List<TrafficView> traffic() {
        return SNAPSHOT.traffic();
    }

    public static List<ConsentView> gpsConsents() {
        return SNAPSHOT.gpsConsents();
    }

    public static List<InsightView> mlInsights() {
        return SNAPSHOT.mlInsights();
    }

    public static List<AuditView> auditEntries() {
        return SNAPSHOT.auditEntries();
    }

    public static List<AssetView> mediaAssets() {
        return SNAPSHOT.mediaAssets();
    }

    public static List<OrganizationView> organizations() {
        return SNAPSHOT.organizations();
    }

    public static List<UserView> users() {
        return SNAPSHOT.users();
    }

    public static List<IntegrationView> integrations() {
        return SNAPSHOT.integrations();
    }

    public static List<MetricView> operationsSummary() {
        return SNAPSHOT.operationsSummary();
    }

    public static List<MetricView> adminSummary() {
        return SNAPSHOT.adminSummary();
    }

    public static List<TransportProviderView> transportProviders() {
        return SNAPSHOT.transportProviders();
    }

    private static CatalogueSnapshot loadSnapshot() {
        return new CatalogueSnapshot(
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of());
    }

}