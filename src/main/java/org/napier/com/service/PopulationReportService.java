package org.napier.com.service;

import org.napier.com.model.PopulationBreakdown;
import org.napier.com.repository.PopulationBreakdownRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Delegates population-breakdown report requests to the repository.
 */
public class PopulationReportService {
    private final PopulationBreakdownRepository repository;

    /**
     * Creates the service with its population-breakdown repository.
     */
    public PopulationReportService(PopulationBreakdownRepository repository) {
        this.repository = repository;
    }

    /**
     * Generates the population breakdown for every country.
     *
     * @return country rows ordered by descending total population
     */
    public List<PopulationBreakdown> generateCountryReport(
            Connection connection) throws SQLException {
        return repository.findPopulationBreakdownByCountry(connection);
    }

    /**
     * Generates the total population of all countries.
     *
     * @return world population as a 64-bit value
     */
    public long generateWorldTotalPopulation(Connection connection)
            throws SQLException {
        return repository.findWorldTotalPopulation(connection);
    }

    /**
     * Generates the population breakdown for every continent.
     *
     * @return continent rows ordered by descending total population
     */
    public List<PopulationBreakdown> generateContinentReport(
            Connection connection) throws SQLException {
        return repository.findPopulationBreakdownByContinent(connection);
    }

    /**
     * Generates the population breakdown for every region.
     *
     * @return region rows ordered by descending total population
     */
    public List<PopulationBreakdown> generateRegionReport(
            Connection connection) throws SQLException {
        return repository.findPopulationBreakdownByRegion(connection);
    }
}
