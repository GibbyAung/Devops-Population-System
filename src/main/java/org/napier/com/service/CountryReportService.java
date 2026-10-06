package org.napier.com.service;

import org.napier.com.model.Country;
import org.napier.com.repository.CountryRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CountryReportService {
    private final CountryRepository repository;

    public CountryReportService(CountryRepository repository) {
        this.repository = repository;
    }

    public List<Country> generateWorldReport(Connection connection)
            throws SQLException {
        return repository.findAllCountries(connection);
    }

    public List<Country> generateRegionReport(
            Connection connection,
            String region) throws SQLException {

        return repository.findCountriesByRegion(connection, region);
    }

    public List<Country> generateContinentReport(
            Connection connection,
            String continent) throws SQLException {
        return repository.findCountriesByContinent(
                connection,
                continent
        );
    }

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

    private void validateLimit(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException(
                    "N must be a positive whole number."
            );
        }
    }

    private void validateScope(String scope, String label) {
        if (scope == null || scope.isBlank()) {
            throw new IllegalArgumentException(
                    label + " must be provided."
            );
        }
    }

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