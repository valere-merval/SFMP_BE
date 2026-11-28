package com.smartfan.mobility.bootstrap;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(0)
public class DemoDataResetSeeder implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DemoDataResetSeeder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void run(String... args) {
        List<String> tables = jdbcTemplate.queryForList(
                "SELECT tablename FROM pg_tables WHERE schemaname = 'public' AND tablename <> 'flyway_schema_history'",
                String.class);
        if (tables.isEmpty()) {
            return;
        }
        String joined = String.join(", ", tables.stream().map(name -> '"' + name + '"').toList());
        jdbcTemplate.execute("TRUNCATE TABLE " + joined + " RESTART IDENTITY CASCADE");
    }
}
