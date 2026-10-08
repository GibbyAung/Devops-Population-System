package org.napier.com.service;

import org.junit.jupiter.api.Test;
import org.napier.com.repository.CountryRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CountryReportServiceTest {

    private final CountryReportService service =
            new CountryReportService(new CountryRepository());

    @Test
    void rejectsZeroForContinentTopN() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateContinentTopNReport(
                        null,
                        "Asia",
                        0
                )
        );

    }

    @Test
    void rejectsNegativeForRegionTopN() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateRegionTopNReport(
                        null,
                        "Eastern Asia",
                        -1
                )
        );
    }

    @Test
    void rejectsBlankContinent() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateContinentTopNReport(
                        null,
                        "   ",
                        5
                )
        );
    }

    @Test
    void rejectsBlankRegion() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateRegionTopNReport(
                        null,
                        "",
                        5
                )
        );
    }
}