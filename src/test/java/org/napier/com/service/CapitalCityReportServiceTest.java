package org.napier.com.service;

import org.junit.jupiter.api.Test;
import org.napier.com.repository.CapitalCityRepository;

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
}
