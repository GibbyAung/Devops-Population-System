package org.napier.com.service;

import org.napier.com.model.CapitalCity;
import org.napier.com.repository.CapitalCityRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Validates capital-city report requests and delegates them to the repository.
 */
public class CapitalCityReportService {
    private final CapitalCityRepository repository;

    /**
     * Creates the service with its capital-city data repository.
     */
    public CapitalCityReportService(CapitalCityRepository repository) {
        this.repository = repository;
    }

    /**
     * Generates the worldwide capital-city report.
     *
     * @return all capital cities ordered by descending population
     */
    public List<CapitalCity> generateWorldReport(Connection connection)
            throws SQLException {
        return repository.findAllCapitalCities(connection);
    }

    /**
     * Generates a capital-city report for one continent.
     *
     * @return matching capital cities ordered by descending population
     */
    public List<CapitalCity> generateContinentReport(
            Connection connection,
            String continent) throws SQLException {

        validateScope(continent, "Continent");
        return repository.findCapitalCitiesByContinent(
                connection,
                continent
        );
    }

    public List<CapitalCity> generateRegionReport(
            Connection connection,
            String region) throws SQLException {

        validateScope(region, "Region");

        return repository.findCapitalCitiesByRegion(
                connection,
                region
        );
    }

    /**
     * Generates a Top-N worldwide capital-city report.
     *
     * @return up to N capital cities ordered by descending population
     */
    public List<CapitalCity> generateTopNWorldReport(
            Connection connection,
            int n) throws SQLException {

        validateLimit(n);
        return repository.findTopNCapitalCities(connection, n);
    }

    /**
     * Rejects a Top-N limit that is not positive.
     */
    private void validateLimit(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException(
                    "N must be a positive whole number."
            );
        }
    }

    /**
     * Rejects a missing or blank geographic scope.
     */
    private void validateScope(String scope, String label) {
        if (scope == null || scope.isBlank()) {
            throw new IllegalArgumentException(
                    label + " must be provided."
            );
        }
    }
}
