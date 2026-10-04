package org.napier.com.service;

import org.junit.jupiter.api.Test;
import org.napier.com.repository.CountryRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CountryReportServiceTest {

    private final CountryReportService service =
            new CountryReportService(new CountryRepository());

    @Test
    void rejectsZeroBeforeDatabaseQuery() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateWorldTopNReport(null, 0)
        );
    }

    @Test
    void rejectsNegativeBeforeDatabaseQuery() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateWorldTopNReport(null, -5)
        );
    }
}