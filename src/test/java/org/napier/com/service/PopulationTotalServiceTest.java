package org.napier.com.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.napier.com.model.PopulationTotal;
import org.napier.com.repository.PopulationTotalRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PopulationTotalCityServiceTest {

    private final PopulationTotalService service =
            new PopulationTotalService(new PopulationTotalRepository() {
                @Override
                public Optional<PopulationTotal> findTotalByCity(
                        Connection connection,
                        String cityName) throws SQLException {

                    return switch (cityName) {
                        case "Yangon" -> Optional.of(
                                new PopulationTotal("Yangon", 4581000L)
                        );
                        // Represents the agreed duplicate-name behavior.
                        case "Duplicate Name" -> Optional.of(
                                new PopulationTotal("Duplicate Name", 12000L)
                        );
                        case "Zero City" -> Optional.of(
                                new PopulationTotal("Zero City", 0L)
                        );
                        case "Database failure" ->
                                throw new SQLException("City query failed");
                        default -> Optional.empty();
                    };
                }
            });

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t"})
    void rejectsMissingCity(String input) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.generateCityReport(null, input)
        );

        assertEquals("City must be provided.", exception.getMessage());
    }

    @Test
    void stripsCityNameWhitespace() throws SQLException {
        PopulationTotal result =
                service.generateCityReport(null, " Yangon ");

        assertEquals("Yangon", result.getName());
        assertEquals(4581000L, result.getTotalPopulation());
    }

    @Test
    void rejectsUnknownCity() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.generateCityReport(null, "No Such City")
        );

        assertEquals("Unknown city: No Such City", exception.getMessage());
    }

    @Test
    void preservesKnownZeroPopulation() throws SQLException {
        PopulationTotal result =
                service.generateCityReport(null, "Zero City");

        assertEquals(0L, result.getTotalPopulation());
    }

    @Test
    void returnsTheAgreedDuplicateNameResult() throws SQLException {
        PopulationTotal result =
                service.generateCityReport(null, "Duplicate Name");

        assertEquals("Duplicate Name", result.getName());
        assertEquals(12000L, result.getTotalPopulation());
    }

    @Test
    void propagatesDatabaseFailure() {
        assertThrows(
                SQLException.class,
                () -> service.generateCityReport(null, "Database failure")
        );
    }
}