package org.napier.com;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * End-to-end command tests that exercise Main against the Compose MySQL DB.
 * Run with {@code mvn clean verify -Pintegration-tests} and DB_* variables.
 */
class MainCommandIT {

    @BeforeEach
    void requireLiveMySqlDatabase() throws SQLException {
        String host = System.getenv("DB_HOST");
        assumeTrue(host != null && !host.isBlank(),
                "Set DB_HOST to run application command integration tests");

        String port = valueOrDefault("DB_PORT", "3306");
        String database = valueOrDefault("DB_NAME", "world");
        String user = valueOrDefault("DB_USER", "devops");
        String password = valueOrDefault("DB_PASSWORD", "devopspassword");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";

        try (Connection ignored = DriverManager.getConnection(url, user, password)) {
            // Validate the database before calling Main, whose CLI error path exits the JVM.
        }
    }

    @Test
    void capitalAndCityCommandsReachTheirReportHandlers() throws Exception {
        assertReport(new String[]{"capital-world"},
                "All capital cities in the world");
        assertReport(new String[]{"capital-top-world", "3"},
                "Top 3 populated capital cities in the world");
        assertReport(new String[]{"city-total-population", "London"},
                "London");
    }

    @Test
    void populationBreakdownAndTotalCommandsReachTheirReportHandlers()
            throws Exception {
        assertReport(new String[]{"country-population"},
                "Population breakdown by country");
        assertReport(new String[]{"world-population"},
                "World total population");
        assertReport(new String[]{"continent-population"},
                "Population breakdown by continent");
        assertReport(new String[]{"region-population"},
                "Population breakdown by region");
        assertReport(new String[]{"continent-total-population", "Asia"},
                "Total population of continent: Asia");
        assertReport(new String[]{"region-total-population", "Southeast Asia"},
                "Southeast Asia");
        assertReport(new String[]{"country-total-population", "United Kingdom"},
                "United Kingdom");
        assertReport(new String[]{"district-total-population", "California"},
                "California");
    }

    private void assertReport(String[] arguments, String expectedText)
            throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try (PrintStream captured = new PrintStream(
                bytes,
                true,
                StandardCharsets.UTF_8)) {
            System.setOut(captured);
            Main.main(arguments);
        } finally {
            System.setOut(original);
        }

        assertTrue(
                bytes.toString(StandardCharsets.UTF_8).contains(expectedText),
                "Expected command output to contain: " + expectedText
        );
    }

    private static String valueOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
