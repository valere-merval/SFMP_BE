package com.smartfan.mobility.shared.bootstrap;

import java.time.LocalDate;
import java.util.List;

public final class DemoVenueData {

    private DemoVenueData() {
    }

    public record VenueSeed(
            String matchId,
            String catalogTitle,
            String homeTeam,
            String awayTeam,
            String homeFlag,
            String awayFlag,
            String city,
            String country,
            String stadium,
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

    public static List<VenueSeed> matchSeeds() {
        return List.of(
                new VenueSeed("rma-bayern-bernabeu", "Real Madrid vs Bayern München", "Real Madrid", "Bayern München", "🇪🇸", "🇩🇪", "Madrid", "Spain", "Santiago Bernabéu", "Champions Stage", LocalDate.of(2026, 12, 4), "21:00", "Gate B", "Lower West", "2h 30m", "Clear", "+16°C", "Metro, Cercanías and tram links ready for fans.", "€79", 40.4531, -3.6883),
                new VenueSeed("atm-dortmund-metropolitano", "Atlético Madrid vs Borussia Dortmund", "Atlético Madrid", "Borussia Dortmund", "🇪🇸", "🇩🇪", "Madrid", "Spain", "Riyadh Air Metropolitano", "Quarterfinal Stage", LocalDate.of(2026, 12, 8), "20:45", "Gate A", "North Stand", "1h 55m", "Partly Cloudy", "+15°C", "Bus, metro and rail lines are active around the venue.", "€68", 40.4362, -3.5997),
                new VenueSeed("bar-manu-campnou", "FC Barcelona vs Manchester United", "FC Barcelona", "Manchester United", "🇪🇸", "🇬🇧", "Barcelona", "Spain", "Spotify Camp Nou", "League Stage", LocalDate.of(2026, 12, 12), "21:00", "Gate 12", "South Curve", "3h 05m", "Mild", "+18°C", "Rail, bus and tram interchanges are open for matchday.", "€74", 41.3809, 2.1228),
                new VenueSeed("schalke-paris-veltins", "Schalke 04 vs Paris Saint-Germain", "Schalke 04", "Paris Saint-Germain", "🇩🇪", "🇫🇷", "Gelsenkirchen", "Germany", "Veltins-Arena", "Knockout Stage", LocalDate.of(2026, 12, 15), "20:30", "Gate 4", "East Tier", "2h 10m", "Cool", "+11°C", "Train and bus connections run directly to the arena district.", "€62", 51.5549, 7.0677),
                new VenueSeed("bayern-juventus-allianz", "Bayern München vs Juventus", "Bayern München", "Juventus", "🇩🇪", "🇮🇹", "Munich", "Germany", "Allianz Arena", "Semifinal Stage", LocalDate.of(2026, 12, 19), "21:00", "Gate 1", "Upper East", "2h 45m", "Clear", "+9°C", "S-Bahn, bus and tram corridors connect directly to the stadium.", "€81", 48.2188, 11.6247),
                new VenueSeed("dortmund-liverpool-signal", "Borussia Dortmund vs Liverpool", "Borussia Dortmund", "Liverpool", "🇩🇪", "🇬🇧", "Dortmund", "Germany", "Signal Iduna Park", "Quarterfinal Stage", LocalDate.of(2026, 12, 22), "20:45", "Gate 8", "South Stand", "1h 35m", "Cold", "+7°C", "Rail, bus and pedestrian routes are highlighted in live navigation.", "€66", 51.4926, 7.4519),
                new VenueSeed("wembley-chelsea-wembley", "Chelsea vs Manchester City", "Chelsea", "Manchester City", "🇬🇧", "🇬🇧", "London", "United Kingdom", "Wembley Stadium", "Final Stage", LocalDate.of(2026, 12, 26), "19:30", "Gate 18", "Club Wembley", "2h 50m", "Cloudy", "+12°C", "Underground, rail and bus flows are active around Wembley.", "£89", 51.5560, -0.2796),
                new VenueSeed("stamford-liverpool-bridge", "Chelsea vs Liverpool", "Chelsea", "Liverpool", "🇬🇧", "🇬🇧", "London", "United Kingdom", "Stamford Bridge", "League Stage", LocalDate.of(2026, 12, 28), "20:00", "Gate 7", "West Lower", "2h 05m", "Rainy", "+10°C", "Rail and bus access is live for the stadium district.", "£77", 51.4817, -0.1905),
                new VenueSeed("milan-juventus-san-siro", "AC Milan vs Juventus", "AC Milan", "Juventus", "🇮🇹", "🇮🇹", "Milan", "Italy", "Stadio San Siro", "Derby Stage", LocalDate.of(2026, 12, 30), "20:45", "Gate 5", "North Ring", "2h 20m", "Clear", "+8°C", "Metro and bus lines feed the stadium precinct.", "€73", 45.4781, 9.1240),
                new VenueSeed("manu-mancity-oldtrafford", "Manchester United vs Manchester City", "Manchester United", "Manchester City", "🇬🇧", "🇬🇧", "Manchester", "United Kingdom", "Old Trafford", "Derby Stage", LocalDate.of(2027, 1, 2), "20:00", "Gate 3", "East Stand", "3h 20m", "Windy", "+6°C", "Trains, buses and walking routes are available in live mode.", "£85", 53.4631, -2.2913),
                new VenueSeed("mancity-liverpool-etihad", "Manchester City vs Liverpool", "Manchester City", "Liverpool", "🇬🇧", "🇬🇧", "Manchester", "United Kingdom", "Etihad Stadium", "League Stage", LocalDate.of(2027, 1, 5), "20:00", "Gate 2", "North Stand", "2h 40m", "Cold", "+5°C", "Bus and rail interchanges support the full matchday flow.", "£84", 53.4831, -2.2004),
                new VenueSeed("paris-marseille-parc", "Paris Saint-Germain vs Marseille", "Paris Saint-Germain", "Marseille", "🇫🇷", "🇫🇷", "Paris", "France", "Parc des Princes", "Classic Stage", LocalDate.of(2027, 1, 8), "21:00", "Gate 10", "West Tribune", "2h 15m", "Clear", "+9°C", "Metro, tram and bus lines are staged for live arrival guidance.", "€75", 48.8414, 2.2530),
                new VenueSeed("marseille-lyon-velodrome", "Marseille vs Lyon", "Marseille", "Lyon", "🇫🇷", "🇫🇷", "Marseille", "France", "Orange Vélodrome", "League Stage", LocalDate.of(2027, 1, 11), "20:45", "Gate 6", "South Bowl", "2h 05m", "Mild", "+13°C", "Bus and rail routes are prepared around the stadium area.", "€61", 43.2698, 5.3957),
                new VenueSeed("lyon-psg-groupama", "Lyon vs Paris Saint-Germain", "Lyon", "Paris Saint-Germain", "🇫🇷", "🇫🇷", "Lyon", "France", "Groupama Stadium", "Knockout Stage", LocalDate.of(2027, 1, 14), "21:00", "Gate 9", "East Ring", "1h 55m", "Cloudy", "+8°C", "Tram, bus and rail routes are live for fan arrival.", "€69", 45.7650, 4.9821));
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
                new MobilitySeed("groupama", "Lyon", "France", "Groupama Stadium", 45.7650, 4.9821));
    }

    public static String stadiumImageUrl(String stadium, String city) {
        String query = (stadium + " " + city + " stadium").replace(' ', ',');
        return "https://source.unsplash.com/featured/1200x800/?" + query;
    }
}
