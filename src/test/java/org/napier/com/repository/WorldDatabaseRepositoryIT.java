package org.napier.com.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.napier.com.model.CapitalCity;
import org.napier.com.model.PopulationBreakdown;
import org.napier.com.model.PopulationTotal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Repository integration tests against the real MySQL world database.
 * Run with {@code mvn clean verify -Pintegration-tests} and DB_* variables.
 */
class WorldDatabaseRepositoryIT {

    private Connection connection;

    @BeforeEach
    void connectToMySqlWhenConfigured() throws SQLException {
        String host = System.getenv("DB_HOST");
        assumeTrue(host != null && !host.isBlank(),
                "Set DB_HOST to run MySQL integration tests");

        String port = valueOrDefault("DB_PORT", "3306");
        String database = valueOrDefault("DB_NAME", "world");
        String user = valueOrDefault("DB_USER", "devops");
        String password = valueOrDefault("DB_PASSWORD", "devopspassword");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";

        connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
    }

    @AfterEach
    void rollbackAndCloseConnection() throws SQLException {
        if (connection != null) {
            try {
                connection.rollback();
            } finally {
                connection.close();
            }
        }
    }

    @Test
    void populationTotalRepositoryAggregatesDuplicateCityNames()
            throws SQLException {
        insertCity("CR2 Duplicate City", "USA", "CR2 Test District", 120);
        insertCity("CR2 Duplicate City", "USA", "CR2 Test District", 340);

        Optional<PopulationTotal> result =
                new PopulationTotalRepository().findTotalByCity(
                        connection,
                        "CR2 Duplicate City"
                );

        assertTrue(result.isPresent());
        assertEquals("CR2 Duplicate City", result.get().getName());
        assertEquals(460L, result.get().getTotalPopulation());
    }

    @Test
    void capitalCityRepositoryReturnsOrderedRowsFromWorldDatabase()
            throws SQLException {
        List<CapitalCity> capitals =
                new CapitalCityRepository().findTopNCapitalCities(connection, 5);

        assertEquals(5, capitals.size());
        assertNotNull(capitals.getFirst().getName());
        for (int index = 1; index < capitals.size(); index++) {
            assertTrue(
                    capitals.get(index - 1).getPopulation()
                            >= capitals.get(index).getPopulation(),
                    "Capital cities should be ordered by descending population"
            );
        }
    }

    @Test
    void populationBreakdownRepositoryReadsWorldAndCountryData()
            throws SQLException {
        PopulationBreakdownRepository repository =
                new PopulationBreakdownRepository();

        long worldPopulation = repository.findWorldTotalPopulation(connection);
        List<PopulationBreakdown> countries =
                repository.findPopulationBreakdownByCountry(connection);

        assertTrue(worldPopulation > 0L);
        assertFalse(countries.isEmpty());
        assertTrue(countries.getFirst().getTotalPopulation() > 0L);
    }

    private void insertCity(
            String name,
            String countryCode,
            String district,
            int population) throws SQLException {

        String sql = """
                INSERT INTO city (Name, CountryCode, District, Population)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, countryCode);
            statement.setString(3, district);
            statement.setInt(4, population);
            statement.executeUpdate();
        }
    }

    private static String valueOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
