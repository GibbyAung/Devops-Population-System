package org.napier.com.service;

import org.napier.com.model.City;
import org.napier.com.repository.CityRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CityReportService {
    private final CityRepository repository;

    public CityReportService(CityRepository repository) {
        this.repository = repository;
    }

    public List<City> generateWorldReport(Connection connection)
            throws SQLException {
        return repository.findAllCities(connection);
    }

    public List<City> generateContinentReport(
            Connection connection,
            String continent) throws SQLException {

        validateScope(continent, "Continent");
        return repository.findCitiesByContinent(connection, continent);
    }

    private void validateScope(String scope, String label) {
        if (scope == null || scope.isBlank()) {
            throw new IllegalArgumentException(
                    label + " must be provided."
            );
        }
    }

    private void validateLimit(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException(
                    "N must be a positive whole number."
            );
        }
    }
}