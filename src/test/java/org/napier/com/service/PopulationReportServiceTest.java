package org.napier.com.service;

import org.junit.jupiter.api.Test;
import org.napier.com.model.PopulationBreakdown;
import org.napier.com.repository.PopulationBreakdownRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PopulationReportServiceTest {

    private final PopulationReportService service =
            new PopulationReportService(
                    new PopulationBreakdownRepository()
            );

    @Test
    void delegatesContinentReportToRepository() throws SQLException {
        AtomicBoolean repositoryCalled = new AtomicBoolean(false);

        PopulationBreakdownRepository stubRepository =
                new PopulationBreakdownRepository() {
                    @Override
                    public List<PopulationBreakdown>
                            findPopulationBreakdownByContinent(
                                    Connection connection)
                                    throws SQLException {
                        repositoryCalled.set(true);
                        return List.of(
                                new PopulationBreakdown(
                                        "Asia",
                                        3700000000L,
                                        1200000000L
                                )
                        );
                    }
                };

        PopulationReportService testService =
                new PopulationReportService(stubRepository);
        List<PopulationBreakdown> result =
                testService.generateContinentReport(null);

        assertTrue(repositoryCalled.get());
        assertEquals(1, result.size());
        assertEquals("Asia", result.get(0).getName());
    }

    @Test
    void delegatesRegionReportToRepository() throws SQLException {
        AtomicBoolean repositoryCalled = new AtomicBoolean(false);

        PopulationBreakdownRepository stubRepository =
                new PopulationBreakdownRepository() {
                    @Override
                    public List<PopulationBreakdown>
                            findPopulationBreakdownByRegion(
                                    Connection connection)
                                    throws SQLException {
                        repositoryCalled.set(true);
                        return List.of(
                                new PopulationBreakdown(
                                        "Eastern Asia",
                                        1500000000L,
                                        700000000L
                                )
                        );
                    }
                };

        PopulationReportService testService =
                new PopulationReportService(stubRepository);
        List<PopulationBreakdown> result =
                testService.generateRegionReport(null);

        assertTrue(repositoryCalled.get());
        assertEquals(1, result.size());
        assertEquals("Eastern Asia", result.get(0).getName());
    }
}
