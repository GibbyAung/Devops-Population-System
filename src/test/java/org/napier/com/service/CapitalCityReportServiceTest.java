package org.napier.com.service;

import org.junit.jupiter.api.Test;
import org.napier.com.repository.CapitalCityRepository;

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
}
