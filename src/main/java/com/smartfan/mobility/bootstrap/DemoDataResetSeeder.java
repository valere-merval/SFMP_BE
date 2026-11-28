package com.smartfan.mobility.bootstrap;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("flyway-java-demo")
@Order(0)
public class DemoDataResetSeeder implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DemoDataResetSeeder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void run(String... args) {
        jdbcTemplate.execute((Connection connection) -> {
            List<String> tables = new ArrayList<>();
            DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet rs = metaData.getTables(connection.getCatalog(), "sfmp", "%", new String[]{"TABLE"})) {
                while (rs.next()) {
                    String name = rs.getString("TABLE_NAME");
                    if (name == null || "flyway_schema_history".equalsIgnoreCase(name)) {
                        continue;
                    }
                    tables.add(name);
                }
            }
            if (!tables.isEmpty()) {
                String joined = String.join(", ", tables.stream().map(name -> '"' + name + '"').toList());
                jdbcTemplate.execute("TRUNCATE TABLE " + joined + " RESTART IDENTITY CASCADE");
            }
            return null;
        });
    }
}
