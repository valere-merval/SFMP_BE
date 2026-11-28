package com.smartfan.mobility.shared.bootstrap;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public final class CompetitionDemoData {

    private CompetitionDemoData() {
    }

    public record CompetitionSeed(
            String id,
            String title,
            String description,
            String imageUrl,
            String sport,
            String country,
            String badge,
            int eventCount) {
    }

    public record MatchSeed(
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
            String gate,
            String sector,
            String countdown,
            String weather,
            String temperature,
            String transitSummary,
            String ticketPrice,
            double latitude,
            double longitude) {
    }

    public record MobilitySeed(
            String id,
            String city,
            String country,
            String stadium,
            double latitude,
            double longitude) {
    }

    public static List<CompetitionSeed> competitionSeeds() {
        return List.of(
                new CompetitionSeed(
                        "champions-league",
                        "UEFA Champions League",
                        "Elite European nights with live arrivals, premium transit, and stadium city flows.",
                        "https://source.unsplash.com/featured/1200x800/?UEFA,Champions,League,stadium",
                        "Football",
                        "Europe",
                        "UCL",
                        3),
                new CompetitionSeed(
                        "bundesliga",
                        "Bundesliga",
                        "German matchdays with rail, bus and tram corridors tuned for live navigation.",
                        "https://source.unsplash.com/featured/1200x800/?Bundesliga,stadium,Germany",
                        "Football",
                        "Germany",
                        "DE",
                        3),
                new CompetitionSeed(
                        "ligue-1",
                        "Ligue 1",
                        "French league matchdays with walking routes, rail links and match arrival flows.",
                        "https://source.unsplash.com/featured/1200x800/?Ligue,1,stadium,France",
                        "Football",
                        "France",
                        "FR",
                        3),
                new CompetitionSeed(
                        "la-liga",
                        "La Liga",
                        "Spanish football nights with live station guidance and premium pedestrian routing.",
                        "https://source.unsplash.com/featured/1200x800/?La,Liga,stadium,Spain",
                        "Football",
                        "Spain",
                        "ES",
                        3),
                new CompetitionSeed(
                        "afcon",
                        "Africa Cup of Nations",
                        "African continental nights with stadium transfers, shuttles and live fan navigation.",
                        "https://source.unsplash.com/featured/1200x800/?Africa,Cup,of,Nations,stadium",
                        "Football",
                        "Africa",
                        "AF",
                        3),
                new CompetitionSeed(
                        "world-cup",
                        "FIFA World Cup",
                        "Global tournament matchdays with station guidance and long-range travel flows.",
                        "https://source.unsplash.com/featured/1200x800/?FIFA,World,Cup,stadium",
                        "Football",
                        "World",
                        "WC",
                        3));
    }

    public static List<MatchSeed> matchSeeds() {
        return List.of(
                new MatchSeed(
                        "ucl-real-bayern-bernabeu",
                        "champions-league",
                        "Real Madrid",
                        "Bayern München",
                        "https://flagcdn.com/w80/es.png",
                        "https://flagcdn.com/w80/de.png",
                        "Madrid",
                        "Spain",
                        "Santiago Bernabéu",
                        "Champions Stage",
                        LocalDate.of(2026, 12, 4),
                        "21:00",
                        "Gate B",
                        "Lower West",
                        "2h 30m",
                        "Clear",
                        "+16°C",
                        "Metro, Cercanías and tram links are ready for the match.",
                        "€79",
                        40.4531,
                        -3.6883),
                new MatchSeed(
                        "ucl-mancity-psg-etihad",
                        "champions-league",
                        "Manchester City",
                        "Paris Saint-Germain",
                        "https://flagcdn.com/w80/gb.png",
                        "https://flagcdn.com/w80/fr.png",
                        "Manchester",
                        "United Kingdom",
                        "Etihad Stadium",
                        "League Stage",
                        LocalDate.of(2026, 12, 6),
                        "21:00",
                        "Gate 2",
                        "North Stand",
                        "2h 45m",
                        "Cloudy",
                        "+12°C",
                        "Rail and bus links are staged for the live arrival flow.",
                        "£84",
                        53.4831,
                        -2.2004),
                new MatchSeed(
                        "ucl-barca-inter-campnou",
                        "champions-league",
                        "FC Barcelona",
                        "Inter Milan",
                        "https://flagcdn.com/w80/es.png",
                        "https://flagcdn.com/w80/it.png",
                        "Barcelona",
                        "Spain",
                        "Spotify Camp Nou",
                        "Knockout Stage",
                        LocalDate.of(2026, 12, 8),
                        "21:00",
                        "Gate 12",
                        "South Curve",
                        "3h 05m",
                        "Mild",
                        "+18°C",
                        "Rail, bus and tram corridors are open for the stadium district.",
                        "€74",
                        41.3809,
                        2.1228),
                new MatchSeed(
                        "bundesliga-bayern-dortmund-allianz",
                        "bundesliga",
                        "Bayern München",
                        "Borussia Dortmund",
                        "https://flagcdn.com/w80/de.png",
                        "https://flagcdn.com/w80/de.png",
                        "Munich",
                        "Germany",
                        "Allianz Arena",
                        "Top Match",
                        LocalDate.of(2026, 12, 10),
                        "20:30",
                        "Gate 1",
                        "Upper East",
                        "2h 35m",
                        "Clear",
                        "+9°C",
                        "S-Bahn, bus and tram corridors connect directly to the stadium.",
                        "€81",
                        48.2188,
                        11.6247),
                new MatchSeed(
                        "bundesliga-schalke-leipzig-veltins",
                        "bundesliga",
                        "Schalke 04",
                        "RB Leipzig",
                        "https://flagcdn.com/w80/de.png",
                        "https://flagcdn.com/w80/de.png",
                        "Gelsenkirchen",
                        "Germany",
                        "Veltins-Arena",
                        "Weekend Match",
                        LocalDate.of(2026, 12, 12),
                        "18:30",
                        "Gate 4",
                        "East Tier",
                        "2h 10m",
                        "Cool",
                        "+11°C",
                        "Train and bus connections run directly to the arena district.",
                        "€62",
                        51.5549,
                        7.0677),
                new MatchSeed(
                        "bundesliga-frankfurt-leverkusen-park",
                        "bundesliga",
                        "Eintracht Frankfurt",
                        "Bayer Leverkusen",
                        "https://flagcdn.com/w80/de.png",
                        "https://flagcdn.com/w80/de.png",
                        "Frankfurt",
                        "Germany",
                        "Deutsche Bank Park",
                        "Prime Match",
                        LocalDate.of(2026, 12, 14),
                        "20:30",
                        "Gate 7",
                        "South Bowl",
                        "1h 55m",
                        "Windy",
                        "+10°C",
                        "Rail, bus and pedestrian routes are staged for the arena area.",
                        "€66",
                        50.0685,
                        8.6450),
                new MatchSeed(
                        "ligue1-psg-marseille-parc",
                        "ligue-1",
                        "Paris Saint-Germain",
                        "Marseille",
                        "https://flagcdn.com/w80/fr.png",
                        "https://flagcdn.com/w80/fr.png",
                        "Paris",
                        "France",
                        "Parc des Princes",
                        "Classic Match",
                        LocalDate.of(2026, 12, 16),
                        "21:00",
                        "Gate 10",
                        "West Tribune",
                        "2h 15m",
                        "Clear",
                        "+9°C",
                        "Metro, tram and bus lines are staged for live arrival guidance.",
                        "€75",
                        48.8414,
                        2.2530),
                new MatchSeed(
                        "ligue1-lyon-lille-groupama",
                        "ligue-1",
                        "Lyon",
                        "Lille",
                        "https://flagcdn.com/w80/fr.png",
                        "https://flagcdn.com/w80/fr.png",
                        "Lyon",
                        "France",
                        "Groupama Stadium",
                        "League Night",
                        LocalDate.of(2026, 12, 18),
                        "20:45",
                        "Gate 9",
                        "East Ring",
                        "2h 00m",
                        "Cloudy",
                        "+8°C",
                        "Tram, bus and rail routes are active for the stadium.",
                        "€69",
                        45.7650,
                        4.9821),
                new MatchSeed(
                        "ligue1-monaco-nice-louisii",
                        "ligue-1",
                        "Monaco",
                        "Nice",
                        "https://flagcdn.com/w80/fr.png",
                        "https://flagcdn.com/w80/fr.png",
                        "Monaco",
                        "Monaco",
                        "Stade Louis II",
                        "Riviera Match",
                        LocalDate.of(2026, 12, 20),
                        "19:00",
                        "Gate 3",
                        "North Stand",
                        "1h 45m",
                        "Mild",
                        "+14°C",
                        "Walking routes and shuttle links are highlighted live.",
                        "€58",
                        43.7348,
                        7.4217),
                new MatchSeed(
                        "laliga-atleti-real-metropolitano",
                        "la-liga",
                        "Atlético Madrid",
                        "Real Madrid",
                        "https://flagcdn.com/w80/es.png",
                        "https://flagcdn.com/w80/es.png",
                        "Madrid",
                        "Spain",
                        "Riyadh Air Metropolitano",
                        "Derby Stage",
                        LocalDate.of(2026, 12, 22),
                        "21:00",
                        "Gate A",
                        "North Stand",
                        "1h 50m",
                        "Partly Cloudy",
                        "+15°C",
                        "Bus, metro and rail lines are active around the venue.",
                        "€68",
                        40.4362,
                        -3.5997),
                new MatchSeed(
                        "laliga-barca-sevilla-campnou",
                        "la-liga",
                        "FC Barcelona",
                        "Sevilla",
                        "https://flagcdn.com/w80/es.png",
                        "https://flagcdn.com/w80/es.png",
                        "Barcelona",
                        "Spain",
                        "Spotify Camp Nou",
                        "League Match",
                        LocalDate.of(2026, 12, 24),
                        "20:00",
                        "Gate 5",
                        "North Curve",
                        "2h 20m",
                        "Mild",
                        "+17°C",
                        "Rail, bus and tram access is active for matchday arrivals.",
                        "€73",
                        41.3809,
                        2.1228),
                new MatchSeed(
                        "laliga-valencia-athletic-mestalla",
                        "la-liga",
                        "Valencia",
                        "Athletic Club",
                        "https://flagcdn.com/w80/es.png",
                        "https://flagcdn.com/w80/es.png",
                        "Valencia",
                        "Spain",
                        "Mestalla Stadium",
                        "Sunday Match",
                        LocalDate.of(2026, 12, 26),
                        "18:30",
                        "Gate 8",
                        "South End",
                        "2h 05m",
                        "Clear",
                        "+16°C",
                        "Walking, bus and tram access guide fans to the stadium.",
                        "€60",
                        39.4746,
                        -0.3589),
                new MatchSeed(
                        "afcon-senegal-morocco-casablanca",
                        "afcon",
                        "Senegal",
                        "Morocco",
                        "https://flagcdn.com/w80/sn.png",
                        "https://flagcdn.com/w80/ma.png",
                        "Casablanca",
                        "Morocco",
                        "Stade Mohammed V",
                        "Group Stage",
                        LocalDate.of(2026, 12, 28),
                        "20:00",
                        "Gate 6",
                        "East Stand",
                        "2h 25m",
                        "Warm",
                        "+21°C",
                        "Bus and rail services are staged for the match precinct.",
                        "MAD 120",
                        33.5748,
                        -7.6540),
                new MatchSeed(
                        "afcon-egypt-ghana-cairo",
                        "afcon",
                        "Egypt",
                        "Ghana",
                        "https://flagcdn.com/w80/eg.png",
                        "https://flagcdn.com/w80/gh.png",
                        "Cairo",
                        "Egypt",
                        "Cairo International Stadium",
                        "Knockout Stage",
                        LocalDate.of(2026, 12, 30),
                        "19:30",
                        "Gate 2",
                        "North Bowl",
                        "1h 55m",
                        "Clear",
                        "+19°C",
                        "Live walking routes and shuttle flows surround the stadium.",
                        "EGP 1,000",
                        30.0738,
                        31.3217),
                new MatchSeed(
                        "afcon-cameroon-algeria-olembe",
                        "afcon",
                        "Cameroon",
                        "Algeria",
                        "https://flagcdn.com/w80/cm.png",
                        "https://flagcdn.com/w80/dz.png",
                        "Yaoundé",
                        "Cameroon",
                        "Olembe Stadium",
                        "Quarterfinal",
                        LocalDate.of(2027, 1, 2),
                        "20:00",
                        "Gate 9",
                        "West Gate",
                        "2h 10m",
                        "Humid",
                        "+28°C",
                        "Walking and bus navigation are active for the stadium approach.",
                        "XAF 18,000",
                        3.9744,
                        11.5173),
                new MatchSeed(
                        "worldcup-france-brazil-stadefrance",
                        "world-cup",
                        "France",
                        "Brazil",
                        "https://flagcdn.com/w80/fr.png",
                        "https://flagcdn.com/w80/br.png",
                        "Saint-Denis",
                        "France",
                        "Stade de France",
                        "World Cup Night",
                        LocalDate.of(2027, 1, 4),
                        "21:00",
                        "Gate 14",
                        "Premium West",
                        "3h 10m",
                        "Cold",
                        "+7°C",
                        "Rail, bus and pedestrian routing are active for the stadium.",
                        "€88",
                        48.9245,
                        2.3600),
                new MatchSeed(
                        "worldcup-england-germany-wembley",
                        "world-cup",
                        "England",
                        "Germany",
                        "https://flagcdn.com/w80/gb.png",
                        "https://flagcdn.com/w80/de.png",
                        "London",
                        "United Kingdom",
                        "Wembley Stadium",
                        "Knockout Night",
                        LocalDate.of(2027, 1, 6),
                        "20:00",
                        "Gate 18",
                        "Club Wembley",
                        "2h 55m",
                        "Cloudy",
                        "+11°C",
                        "Underground, rail and bus flows are active around Wembley.",
                        "£95",
                        51.5560,
                        -0.2796),
                new MatchSeed(
                        "worldcup-argentina-spain-bernabeu",
                        "world-cup",
                        "Argentina",
                        "Spain",
                        "https://flagcdn.com/w80/ar.png",
                        "https://flagcdn.com/w80/es.png",
                        "Madrid",
                        "Spain",
                        "Santiago Bernabéu",
                        "Final Stage",
                        LocalDate.of(2027, 1, 8),
                        "21:00",
                        "Gate 3",
                        "Upper East",
                        "2h 40m",
                        "Clear",
                        "+13°C",
                        "Metro, bus and walking routes are staged for the venue.",
                        "€92",
                        40.4531,
                        -3.6883));
    }

    public static List<MobilitySeed> mobilitySeeds() {
        return List.of(
                new MobilitySeed("bernabeu", "Madrid", "Spain", "Santiago Bernabéu", 40.4531, -3.6883),
                new MobilitySeed("metropolitano", "Madrid", "Spain", "Riyadh Air Metropolitano", 40.4362, -3.5997),
                new MobilitySeed("campnou", "Barcelona", "Spain", "Spotify Camp Nou", 41.3809, 2.1228),
                new MobilitySeed("veltins", "Gelsenkirchen", "Germany", "Veltins-Arena", 51.5549, 7.0677),
                new MobilitySeed("allianz-munich", "Munich", "Germany", "Allianz Arena", 48.2188, 11.6247),
                new MobilitySeed("signal", "Dortmund", "Germany", "Signal Iduna Park", 51.4926, 7.4519),
                new MobilitySeed("wembley", "London", "United Kingdom", "Wembley Stadium", 51.5560, -0.2796),
                new MobilitySeed("bridge", "London", "United Kingdom", "Stamford Bridge", 51.4817, -0.1905),
                new MobilitySeed("anfield", "Liverpool", "United Kingdom", "Anfield", 53.4308, -2.9608),
                new MobilitySeed("oldtrafford", "Manchester", "United Kingdom", "Old Trafford", 53.4631, -2.2913),
                new MobilitySeed("etihad", "Manchester", "United Kingdom", "Etihad Stadium", 53.4831, -2.2004),
                new MobilitySeed("san-siro", "Milan", "Italy", "Stadio San Siro", 45.4781, 9.1240),
                new MobilitySeed("allianz-turin", "Turin", "Italy", "Allianz Stadium", 45.1096, 7.6410),
                new MobilitySeed("parc", "Paris", "France", "Parc des Princes", 48.8414, 2.2530),
                new MobilitySeed("velodrome", "Marseille", "France", "Orange Vélodrome", 43.2698, 5.3957),
                new MobilitySeed("groupama", "Lyon", "France", "Groupama Stadium", 45.7650, 4.9821),
                new MobilitySeed("mestalla", "Valencia", "Spain", "Mestalla Stadium", 39.4746, -0.3589),
                new MobilitySeed("louisii", "Monaco", "Monaco", "Stade Louis II", 43.7348, 7.4217),
                new MobilitySeed("mohammedv", "Casablanca", "Morocco", "Stade Mohammed V", 33.5748, -7.6540),
                new MobilitySeed("cairo", "Cairo", "Egypt", "Cairo International Stadium", 30.0738, 31.3217),
                new MobilitySeed("olembe", "Yaoundé", "Cameroon", "Olembe Stadium", 3.9744, 11.5173),
                new MobilitySeed("stadefrance", "Saint-Denis", "France", "Stade de France", 48.9245, 2.3600));
    }

    public static String competitionImageUrl(String query) {
        return imageUrl(query);
    }

    public static String stadiumImageUrl(String stadium, String city) {
        return imageUrl(stadium + " " + city + " stadium");
    }

    public static String clubBadgeUrl(String teamName) {
        return badgeSvg(teamName, teamInitials(teamName), "#0f172a", "#c8f36a");
    }

    public static String flagUrl(String iso2) {
        return "https://flagcdn.com/w80/" + iso2.toLowerCase(Locale.ROOT) + ".png";
    }

    private static String teamInitials(String teamName) {
        String[] parts = teamName.replace('-', ' ').trim().split("\s+");
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            if (!part.isBlank()) {
                builder.append(Character.toUpperCase(part.charAt(0)));
            }
            if (builder.length() >= 3) {
                break;
            }
        }
        return builder.length() > 0 ? builder.toString() : "FC";
    }

    private static String imageUrl(String query) {
        String label = sanitizeLabel(query);
        String colorA = paletteColor(label, 0);
        String colorB = paletteColor(label, 1);
        String initials = teamInitials(label);
        String svg = "<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 1200 800'>"
                + "<defs><linearGradient id='g' x1='0' y1='0' x2='1' y2='1'>"
                + "<stop offset='0%' stop-color='" + colorA + "'/>"
                + "<stop offset='100%' stop-color='" + colorB + "'/></linearGradient></defs>"
                + "<rect width='1200' height='800' fill='url(#g)'/>"
                + "<circle cx='900' cy='180' r='160' fill='rgba(255,255,255,0.08)'/>"
                + "<circle cx='240' cy='640' r='220' fill='rgba(255,255,255,0.08)'/>"
                + "<text x='80' y='210' fill='#ffffff' font-family='Poppins,Arial,sans-serif' font-size='68' font-weight='700'>" + escapeXml(label) + "</text>"
                + "<text x='80' y='290' fill='rgba(255,255,255,0.92)' font-family='Poppins,Arial,sans-serif' font-size='32'>Smart Fan Mobility</text>"
                + "<rect x='80' y='530' rx='28' ry='28' width='140' height='140' fill='rgba(255,255,255,0.18)' stroke='rgba(255,255,255,0.45)'/>"
                + "<text x='150' y='620' text-anchor='middle' fill='#ffffff' font-family='Poppins,Arial,sans-serif' font-size='52' font-weight='700'>" + escapeXml(initials) + "</text>"
                + "</svg>";
        return svgDataUri(svg);
    }

    private static String badgeSvg(String label, String initials, String background, String foreground) {
        String svg = "<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 80 80'>"
                + "<rect width='80' height='80' rx='20' ry='20' fill='" + background + "'/>"
                + "<circle cx='40' cy='40' r='30' fill='rgba(255,255,255,0.08)'/>"
                + "<text x='40' y='49' text-anchor='middle' fill='" + foreground + "' font-family='Poppins,Arial,sans-serif' font-size='28' font-weight='700'>" + escapeXml(initials) + "</text>"
                + "</svg>";
        return svgDataUri(svg);
    }

    private static String svgDataUri(String svg) {
        return "data:image/svg+xml;charset=utf-8," + URLEncoder.encode(svg, StandardCharsets.UTF_8);
    }

    private static String sanitizeLabel(String query) {
        String cleaned = query == null ? "" : query.replaceAll("\s+", " ").trim();
        return cleaned.isBlank() ? "Stadium" : cleaned;
    }

    private static String paletteColor(String label, int shift) {
        String[] palette = {"0f172a", "1d4ed8", "7c3aed", "0f766e", "c2410c", "be123c", "15803d", "92400e"};
        int index = Math.floorMod(label.toLowerCase(Locale.ROOT).hashCode() + shift, palette.length);
        return "#" + palette[index];
    }

    private static String escapeXml(String text) {
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
