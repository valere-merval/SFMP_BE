package com.smartfan.mobility.shared.demo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public final class DemoCatalogue {

    private DemoCatalogue() {
    }

    public record EventView(String id, String name, String city, String venue, LocalDate date, String status, int expectedAttendance) {
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

    public static List<EventView> events() {
        return List.of(
                new EventView("evt_madrid_final", "UEFA Champions League Final", "Madrid", "Estadio Metropolitano", LocalDate.of(2027, 5, 29), "Sales Open", 68200),
                new EventView("evt_training_day", "Fan Mobility Dry Run", "Madrid", "Estadio Metropolitano", LocalDate.of(2027, 5, 27), "Planning", 1200),
                new EventView("evt_city_experience", "Fan City Experience", "Madrid", "Plaza de Oriente", LocalDate.of(2027, 5, 28), "Published", 5400)
        );
    }

    public static List<VenueView> venues() {
        return List.of(
                new VenueView("ven_metropolitano", "Estadio Metropolitano", "Madrid", "12 transport zones", "8 access points", "68,000"),
                new VenueView("ven_plaza_oriente", "Plaza de Oriente Fan Zone", "Madrid", "4 transport zones", "3 access points", "9,000")
        );
    }

    public static List<MobilityOptionView> mobilityOptions() {
        return List.of(
                new MobilityOptionView("mob_metro", "Metro", "18 min", "€2.10", "Recommended", "Fastest for peak flow"),
                new MobilityOptionView("mob_shuttle", "Official Shuttle", "25 min", "€8.00", "Live", "Best for group transfers"),
                new MobilityOptionView("mob_taxi", "Taxi / Ride-hailing", "22 min", "€24-35", "Available", "Door-to-door convenience"),
                new MobilityOptionView("mob_walk", "Walking", "34 min", "Free", "Available", "Good for nearby hotels")
        );
    }

    public static List<BookingView> bookings() {
        return List.of(
                new BookingView("bkg_1001", "Amina Diallo", "UEFA Champions League Final", "Official Shuttle", "Confirmed", "18:10"),
                new BookingView("bkg_1002", "Jonas Weber", "UEFA Champions League Final", "Metro + Walk", "Pending", "18:25"),
                new BookingView("bkg_1003", "Sara Ahmed", "Fan City Experience", "Taxi", "Cancelled", "17:40")
        );
    }

    public static List<DriverView> drivers() {
        return List.of(
                new DriverView("drv_1", "Carlos Vega", "Van #24", "North Zone", "On Duty"),
                new DriverView("drv_2", "Lucia Martin", "Shuttle #11", "West Zone", "Standby"),
                new DriverView("drv_3", "David Ruiz", "Coach #03", "Airport Corridor", "On Route")
        );
    }

    public static List<PartnerView> partners() {
        return List.of(
                new PartnerView("prt_metro", "Metro de Madrid", "Rail capacity feed", "Madrid city", "Connected"),
                new PartnerView("prt_shuttle", "City Shuttle Co.", "Fleet operations", "Stadium perimeter", "Connected"),
                new PartnerView("prt_taxi", "RideFlow", "Ride-hailing integration", "Metro area", "Pilot")
        );
    }

    public static List<MetricView> crowdMetrics() {
        return List.of(
                new MetricView("crowd_total", "Expected arrivals", "68,200", "+12%"),
                new MetricView("crowd_peak", "Peak window", "18:15-19:10", "Stable"),
                new MetricView("crowd_heat", "Hot zones", "Gate 12, Gate 14", "Rising")
        );
    }

    public static List<ForecastView> forecasts() {
        return List.of(
                new ForecastView("pred_1", "Metro load at 18:30", "91%", "High", "Open additional service trains"),
                new ForecastView("pred_2", "Shuttle demand from airport", "1,820 riders", "Medium", "Increase shuttle frequency"),
                new ForecastView("pred_3", "Post-match departure burst", "18 min spike", "High", "Pre-position vehicles at zones")
        );
    }

    public static List<NotificationView> notifications() {
        return List.of(
                new NotificationView("ntf_1", "Gate change alert", "Push", "Sent", LocalDateTime.of(2027, 5, 29, 17, 40)),
                new NotificationView("ntf_2", "Route disruption update", "SMS", "Queued", LocalDateTime.of(2027, 5, 29, 18, 5)),
                new NotificationView("ntf_3", "Booking confirmed", "App", "Sent", LocalDateTime.of(2027, 5, 29, 16, 55))
        );
    }

    public static List<AnalyticsView> analytics() {
        return List.of(
                new AnalyticsView("ana_sessions", "Daily sessions", "24,880", "events", "+8.4%"),
                new AnalyticsView("ana_clicks", "Plan Journey CTR", "41.2", "%", "+2.1%"),
                new AnalyticsView("ana_bookings", "Booking conversion", "7.8", "%", "+0.9%")
        );
    }

    public static List<RoadmapView> roadmap() {
        return List.of(
                new RoadmapView("Version 1", "MVP", List.of(
                        "Gestion des événements",
                        "Découverte des événements",
                        "Planification des déplacements",
                        "Carte interactive",
                        "Réservation de transport démonstrative",
                        "Dashboard opérationnel",
                        "Collecte d'analytics",
                        "Première démonstration prédictive")),
                new RoadmapView("Version 2", "Pilot Platform", List.of(
                        "Intégration des partenaires de transport",
                        "Gestion des chauffeurs et véhicules",
                        "Tracking GPS avec consentement",
                        "Notifications intelligentes",
                        "Intégration des données de trafic",
                        "Prévisions de demande",
                        "Gestion avancée des zones")),
                new RoadmapView("Version 3", "Intelligent Mobility", List.of(
                        "Machine Learning pour les prévisions",
                        "Heatmaps de mobilité",
                        "Détection des pics de demande",
                        "Optimisation des points de ramassage",
                        "Adaptation des itinéraires",
                        "Communications contextuelles",
                        "Analytics avancées"))
        );
    }

    public static List<HeatmapView> heatmaps() {
        return List.of(
                new HeatmapView("Gate 12", "Critical", "Rising", "Open additional corridor"),
                new HeatmapView("Gate 14", "High", "Stable", "Keep extra staff deployed"),
                new HeatmapView("Metro Plaza", "Medium", "Falling", "Maintain current routing")
        );
    }

    public static List<DemandPeakView> demandPeaks() {
        return List.of(
                new DemandPeakView("18:00-18:45", "High", "Pre-position shuttles"),
                new DemandPeakView("20:55-21:40", "Critical", "Activate overflow lanes"),
                new DemandPeakView("22:15-22:45", "Medium", "Open return-trip wave")
        );
    }

    public static List<RouteAdaptationView> routeAdaptations() {
        return List.of(
                new RouteAdaptationView("Airport Corridor", "Reroute via Ring Road", "Traffic slowdown detected"),
                new RouteAdaptationView("North Zone", "Shorten shuttle loop", "Demand concentrated at gates"),
                new RouteAdaptationView("Fan Village", "Add walking guidance", "Pedestrian flow is under capacity")
        );
    }

    public static List<ContextualMessageView> contextualMessages() {
        return List.of(
                new ContextualMessageView("Fans near Gate 12", "Push", "Use Gate 14 for faster entry.", "Now"),
                new ContextualMessageView("Late arrivals", "SMS", "Your shuttle departs in 12 minutes.", "T-12m"),
                new ContextualMessageView("Drivers", "In-app", "Switch to adaptive route A due to traffic.", "Live")
        );
    }

    public static List<AnalyticsView> advancedAnalytics() {
        return List.of(
                new AnalyticsView("ana_heatmap", "Heatmap density", "78", "%", "+9%"),
                new AnalyticsView("ana_peak", "Demand peak accuracy", "91.3", "%", "+3.2%"),
                new AnalyticsView("ana_route", "Adaptive route savings", "6.8", "min", "+1.4")
        );
    }

    public static List<AuditView> auditEntries() {
        return List.of(
                new AuditView("aud_1", "UPDATED_BOOKING", "ops-admin", "booking:bkg_1001", LocalDateTime.of(2027, 5, 29, 16, 45)),
                new AuditView("aud_2", "CREATED_EVENT", "platform-admin", "event:evt_madrid_final", LocalDateTime.of(2027, 2, 12, 10, 0)),
                new AuditView("aud_3", "PUBLISHED_NOTIFICATION", "system", "notification:ntf_2", LocalDateTime.of(2027, 5, 29, 18, 5))
        );
    }

    public static List<AssetView> mediaAssets() {
        return List.of(
                new AssetView("med_1", "Metropolitano hero image", "image/jpeg", "Ready", "s3://sfmp/events/metropolitano.jpg"),
                new AssetView("med_2", "Club logo bundle", "image/png", "Ready", "s3://sfmp/partners/logos.zip"),
                new AssetView("med_3", "Driver document", "application/pdf", "Processing", "s3://sfmp/docs/driver-24.pdf")
        );
    }

    public static List<OrganizationView> organizations() {
        return List.of(
                new OrganizationView("org_uefa", "UEFA", "Competition owner", "Active"),
                new OrganizationView("org_atleti", "Atlético de Madrid", "Club", "Active"),
                new OrganizationView("org_metro", "Metro de Madrid", "Transport operator", "Pilot")
        );
    }

    public static List<UserView> users() {
        return List.of(
                new UserView("usr_admin", "Valeria Gomez", "Platform Admin", "SFMP", "Active"),
                new UserView("usr_ops", "Hassan Karim", "Operations Lead", "Metro de Madrid", "Active"),
                new UserView("usr_fan", "Alicia Perez", "Fan", "UEFA", "Invited")
        );
    }

    public static List<IntegrationView> integrations() {
        return List.of(
                new IntegrationView("int_kafka", "Kafka", "Connected", "kafka://local-cluster"),
                new IntegrationView("int_minio", "MinIO", "Connected", "http://minio:9000"),
                new IntegrationView("int_postgres", "PostgreSQL", "Connected", "jdbc:postgresql://db:5432/sfmp")
        );
    }

    public static List<MetricView> operationsSummary() {
        return List.of(
                new MetricView("ops_live", "Live vehicles", "124", "+6"),
                new MetricView("ops_bookings", "Bookings processed", "3,842", "+11%"),
                new MetricView("ops_incidents", "Open incidents", "2", "-1"),
                new MetricView("ops_eta", "Average ETA", "19 min", "-3 min")
        );
    }

    public static List<MetricView> adminSummary() {
        return List.of(
                new MetricView("admin_orgs", "Organizations", "12", "+2"),
                new MetricView("admin_users", "Users", "48", "+5"),
                new MetricView("admin_roles", "Roles", "9", "Stable")
        );
    }
}
