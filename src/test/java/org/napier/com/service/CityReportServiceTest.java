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
    @Test
    void rejectsBlankRegion() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateRegionReport(
                        null,
                        "   "
                )
        );
    }

    @Test
    void rejectsBlankCountryCode() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateCountryReport(
                        null,
                        ""
                )
        );
    }

    @Test
    void rejectsNullCountryCode() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateCountryReport(
                        null,
                        null
                )
        );
    }

    @Test
    void rejectsBlankDistrict() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateDistrictReport(
                        null,
                        "   "
                )
        );
    }

    @Test
    void rejectsNullDistrict() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateDistrictReport(
                        null,
                        null
                )
        );
    }

    @Test
    void rejectsZeroTopN() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateTopNWorldReport(
                        null,
                        0
                )
        );
    }

    @Test
    void rejectsNegativeTopN() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateTopNWorldReport(
                        null,
                        -5
                )
        );
    }

    @Test
    void rejectsBlankContinentForTopN() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateTopNContinentReport(
                        null,
                        "   ",
                        10
                )
        );
    }

    @Test
    void rejectsZeroForContinentTopN() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateTopNContinentReport(
                        null,
                        "Asia",
                        0
                )
        );
    }

    @Test
    void rejectsBlankRegionForTopN() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateTopNRegionReport(
                        null,
                        "",
                        10
                )
        );
    }

    @Test
    void rejectsNegativeForRegionTopN() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateTopNRegionReport(
                        null,
                        "Eastern Asia",
                        -1
                )
        );
    }

    @Test
    void rejectsBlankCountryForTopN() {
        assertThrows(IllegalArgumentException.class,
                () -> service.generateTopNCountryReport(null, "   ", 10));
    }

    @Test
    void rejectsNullCountryForTopN() {
        assertThrows(IllegalArgumentException.class,
                () -> service.generateTopNCountryReport(null, null, 10));
    }

    @Test
    void rejectsZeroCountryTopN() {
        assertThrows(IllegalArgumentException.class,
                () -> service.generateTopNCountryReport(null, "CHN", 0));
    }

    @Test
    void rejectsNegativeCountryTopN() {
        assertThrows(IllegalArgumentException.class,
                () -> service.generateTopNCountryReport(null, "CHN", -1));
    }

    @Test
    void rejectsBlankDistrictForTopN() {
        assertThrows(IllegalArgumentException.class,
                () -> service.generateTopNDistrictReport(null, " ", 10));
    }

    @Test
    void rejectsNullDistrictForTopN() {
        assertThrows(IllegalArgumentException.class,
                () -> service.generateTopNDistrictReport(null, null, 10));
    }

    @Test
    void rejectsZeroDistrictTopN() {
        assertThrows(IllegalArgumentException.class,
                () -> service.generateTopNDistrictReport(null, "California", 0));
    }

    @Test
    void rejectsNegativeDistrictTopN() {
        assertThrows(IllegalArgumentException.class,
                () -> service.generateTopNDistrictReport(null, "California", -5));
    }
}