package org.napier.com.service;

import org.napier.com.model.Country;
import org.napier.com.repository.CountryRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Validates country-report requests and delegates them to the repository.
 */
public class CountryReportService {
    private final CountryRepository repository;

    /**
     * Creates the service with its country data repository.
     */
    public CountryReportService(CountryRepository repository) {
        this.repository = repository;
    }

    /**
     * Generates the worldwide country report.
     *
     * @return all countries ordered by descending population
     */
    public List<Country> generateWorldReport(Connection connection)
            throws SQLException {
        return repository.findAllCountries(connection);
    }

    /**
     * Generates a country report for one region.
     *
     * @return matching countries ordered by descending population
     */
    public List<Country> generateRegionReport(
            Connection connection,
            String region) throws SQLException {

        return repository.findCountriesByRegion(connection, region);
    }

    /**
     * Generates a country report for one continent.
     *
     * @return matching countries ordered by descending population
     */
    public List<Country> generateContinentReport(
            Connection connection,
            String continent) throws SQLException {
        return repository.findCountriesByContinent(
                connection,
                continent
        );
    }

    /**
     * Generates a Top-N worldwide country report.
     *
     * @return up to N countries ordered by descending population
     */
    public List<Country> generateWorldTopNReport(
            Connection connection,
            int limit) throws SQLException {

        if (limit <= 0) {
            throw new IllegalArgumentException(
                    "N must be a positive whole number."
            );
        }

        return repository.findTopNCountries(connection, limit);
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

    /**
     * Generates a Top-N country report for one continent.
     *
     * @return up to N matching countries ordered by descending population
     */
    public List<Country> generateContinentTopNReport(
            Connection connection,
            String continent,
            int limit) throws SQLException {

        validateScope(continent, "Continent");
        validateLimit(limit);

        return repository.findTopNCountriesByContinent(
                connection,
                continent,
                limit
        );
    }

    /**
     * Generates a Top-N country report for one region.
     *
     * @return up to N matching countries ordered by descending population
     */
    public List<Country> generateRegionTopNReport(
            Connection connection,
            String region,
            int limit) throws SQLException {

        validateScope(region, "Region");
        validateLimit(limit);

        return repository.findTopNCountriesByRegion(
                connection,
                region,
                limit
        );
    }
}
