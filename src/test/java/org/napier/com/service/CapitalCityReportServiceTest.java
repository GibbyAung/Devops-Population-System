package org.napier.com.service;

import org.junit.jupiter.api.Test;
import org.napier.com.repository.CapitalCityRepository;
import org.napier.com.model.CapitalCity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CapitalCityReportServiceTest {

    private final CapitalCityReportService service =
            new CapitalCityReportService(
                    new CapitalCityRepository()
            );

    @Test
    void rejectsBlankContinent() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateContinentReport(
                        null,
                        "   "
                )
        );
    }

    @Test
    void rejectsNullContinent() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateContinentReport(
                        null,
                        null
                )
        );
    }
    @Test
    void rejectsBlankRegion() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.generateRegionReport(
                        null,
                        "   "
                )
        );

        assertEquals(
                "Region must be provided.",
                exception.getMessage()
        );
    }

    @Test
    void rejectsNullRegion() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.generateRegionReport(
                        null,
                        null
                )
        );

        assertEquals(
                "Region must be provided.",
                exception.getMessage()
        );
    }

    @Test
    void rejectsZeroTopN() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateTopNWorldReport(null, 0)
        );
    }

    @Test
    void rejectsNegativeTopN() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateTopNWorldReport(null, -5)
        );
    }

    @Test
    void rejectsBlankTopNContinent() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.generateTopNContinentReport(null, "   ", 5)
        );
        assertEquals("Continent must be provided.", exception.getMessage());
    }

    @Test
    void rejectsNullTopNContinent() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.generateTopNContinentReport(null, null, 5)
        );
        assertEquals("Continent must be provided.", exception.getMessage());
    }

    @Test
    void rejectsZeroTopNContinent() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.generateTopNContinentReport(null, "Asia", 0)
        );
        assertEquals("N must be a positive whole number.", exception.getMessage());
    }

    @Test
    void rejectsNegativeTopNContinent() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.generateTopNContinentReport(null, "Asia", -5)
        );
        assertEquals("N must be a positive whole number.", exception.getMessage());
    }

    @Test
    void passesValidContinentAndLimitToRepository() throws SQLException {
        AtomicReference<String> receivedContinent = new AtomicReference<>();
        AtomicInteger receivedLimit = new AtomicInteger();

        CapitalCityRepository stubRepository = new CapitalCityRepository() {
            @Override
            public List<CapitalCity> findTopNCapitalCitiesByContinent(
                    Connection connection, String continent, int n)
                    throws SQLException {
                receivedContinent.set(continent);
                receivedLimit.set(n);
                return List.of(
                        new CapitalCity("Example Capital", "Example Country", 100L)
                );
            }
        };

        CapitalCityReportService testService =
                new CapitalCityReportService(stubRepository);
        List<CapitalCity> result =
                testService.generateTopNContinentReport(null, "Asia", 5);

        assertEquals("Asia", receivedContinent.get());
        assertEquals(5, receivedLimit.get());
        assertEquals(1, result.size());
        assertEquals("Example Capital", result.get(0).getName());
    }
}
