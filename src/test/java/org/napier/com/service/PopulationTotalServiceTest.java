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

class PopulationTotalServiceTest {

    private final PopulationTotalService service =
            new PopulationTotalService(
                    new PopulationTotalRepository() {

                        @Override
                        public Optional<PopulationTotal> findTotalByContinent(
                                Connection connection,
                                String continent) {

                            if ("Asia".equals(continent)) {
                                return Optional.of(
                                        new PopulationTotal(
                                                "Asia",
                                                3700000000L
                                        )
                                );
                            }

                            if ("Antarctica".equals(continent)) {
                                return Optional.of(
                                        new PopulationTotal(
                                                "Antarctica",
                                                0L
                                        )
                                );
                            }

                            return Optional.empty();
                        }

                        @Override
                        public Optional<PopulationTotal> findTotalByRegion(
                                Connection connection,
                                String region) {

                            if ("Southeast Asia".equals(region)) {
                                return Optional.of(
                                        new PopulationTotal(
                                                "Southeast Asia",
                                                500000000L
                                        )
                                );
                            }

                            return Optional.empty();
                        }
                    }
            );

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t"})
    void rejectsMissingContinent(String input) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.generateContinentReport(null, input)
        );

        assertEquals(
                "Continent must be provided.",
                exception.getMessage()
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t"})
    void rejectsMissingRegion(String input) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.generateRegionReport(null, input)
        );

        assertEquals(
                "Region must be provided.",
                exception.getMessage()
        );
    }

    @Test
    void rejectsUnknownContinent() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.generateContinentReport(
                        null,
                        "Atlantis"
                )
        );

        assertEquals(
                "Unknown continent: Atlantis",
                exception.getMessage()
        );
    }

    @Test
    void rejectsUnknownRegion() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.generateRegionReport(
                        null,
                        "Atlantis"
                )
        );

        assertEquals(
                "Unknown region: Atlantis",
                exception.getMessage()
        );
    }

    @Test
    void preservesContinentTotalAboveIntegerMaximum()
            throws SQLException {

        PopulationTotal result =
                service.generateContinentReport(null, " Asia ");

        assertEquals("Asia", result.getName());
        assertEquals(3700000000L, result.getTotalPopulation());
    }

    @Test
    void acceptsRegionNameWithSpaces() throws SQLException {
        PopulationTotal result =
                service.generateRegionReport(
                        null,
                        " Southeast Asia "
                );

        assertEquals("Southeast Asia", result.getName());
        assertEquals(500000000L, result.getTotalPopulation());
    }

    @Test
    void acceptsKnownContinentWithZeroPopulation()
            throws SQLException {

        PopulationTotal result =
                service.generateContinentReport(
                        null,
                        "Antarctica"
                );

        assertEquals("Antarctica", result.getName());
        assertEquals(0L, result.getTotalPopulation());
    }
}