package org.napier.com.service;

import org.junit.jupiter.api.Test;
import org.napier.com.repository.CityRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CityReportServiceTest {

    private final CityReportService service =
            new CityReportService(new CityRepository());

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
}