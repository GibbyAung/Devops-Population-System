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

class PopulationTotalCountryDistrictServiceTest {

    private final PopulationTotalService service =
            new PopulationTotalService(
                    new PopulationTotalRepository() {

                        @Override
                        public Optional<PopulationTotal> findTotalByCountry(
                                Connection connection,
                                String countryName) throws SQLException {

                            if ("Database failure".equals(countryName)) {
                                throw new SQLException("Country query failed");
                            }

                            return switch (countryName) {
                                case "Myanmar" -> Optional.of(
                                        new PopulationTotal("Myanmar", 45611000L)
                                );
                                case "United Kingdom" -> Optional.of(
                                        new PopulationTotal(
                                                "United Kingdom", 59623400L
                                        )
                                );
                                case "Zero Country" -> Optional.of(
                                        new PopulationTotal("Zero Country", 0L)
                                );
                                default -> Optional.empty();
                            };
                        }

                        @Override
                        public Optional<PopulationTotal> findTotalByDistrict(
                                Connection connection,
                                String district) throws SQLException {

                            if ("Database failure".equals(district)) {
                                throw new SQLException("District query failed");
                            }

                            return switch (district) {
                                case "California" -> Optional.of(
                                        new PopulationTotal(
                                                "California", 3500000000L
                                        )
                                );
                                case "New South Wales" -> Optional.of(
                                        new PopulationTotal(
                                                "New South Wales", 5000000L
                                        )
                                );
                                case "Zero District" -> Optional.of(
                                        new PopulationTotal("Zero District", 0L)
                                );
                                default -> Optional.empty();
                            };
                        }
                    }
            );

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t"})
    void rejectsMissingCountry(String input) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.generateCountryReport(null, input)
        );

        assertEquals("Country must be provided.", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t"})
    void rejectsMissingDistrict(String input) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.generateDistrictReport(null, input)
        );

        assertEquals("District must be provided.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Atlantis", "MMR", "Myanmar' OR '1'='1"})
    void rejectsUnknownCountryAndCodeAliases(String input) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.generateCountryReport(null, input)
        );

        assertEquals("Unknown country: " + input, exception.getMessage());
    }

    @Test
    void rejectsUnknownDistrict() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.generateDistrictReport(null, "Atlantis")
        );

        assertEquals("Unknown district: Atlantis", exception.getMessage());
    }

    @Test
    void stripsCountryWhitespace() throws SQLException {
        PopulationTotal result =
                service.generateCountryReport(null, " Myanmar ");

        assertEquals("Myanmar", result.getName());
        assertEquals(45611000L, result.getTotalPopulation());
    }

    @Test
    void acceptsFullCountryNameWithSpaces() throws SQLException {
        PopulationTotal result =
                service.generateCountryReport(null, "United Kingdom");

        assertEquals("United Kingdom", result.getName());
        assertEquals(59623400L, result.getTotalPopulation());
    }

    @Test
    void preservesDistrictTotalAboveIntegerMaximum() throws SQLException {
        PopulationTotal result =
                service.generateDistrictReport(null, " California ");

        assertEquals("California", result.getName());
        assertEquals(3500000000L, result.getTotalPopulation());
    }

    @Test
    void acceptsDistrictNameWithSpaces() throws SQLException {
        PopulationTotal result =
                service.generateDistrictReport(null, "New South Wales");

        assertEquals("New South Wales", result.getName());
        assertEquals(5000000L, result.getTotalPopulation());
    }

    @Test
    void acceptsKnownZeroPopulationCountryAndDistrict() throws SQLException {
        assertEquals(
                0L,
                service.generateCountryReport(null, "Zero Country")
                        .getTotalPopulation()
        );
        assertEquals(
                0L,
                service.generateDistrictReport(null, "Zero District")
                        .getTotalPopulation()
        );
    }

    @Test
    void propagatesDatabaseFailuresInsteadOfReturningFakeTotals() {
        assertThrows(
                SQLException.class,
                () -> service.generateCountryReport(null, "Database failure")
        );
        assertThrows(
                SQLException.class,
                () -> service.generateDistrictReport(null, "Database failure")
        );
    }
}