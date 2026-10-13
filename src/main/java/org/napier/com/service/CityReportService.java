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
    public List<City> generateRegionReport(
            Connection connection,
            String region) throws SQLException {

        validateScope(region, "Region");
        return repository.findCitiesByRegion(connection, region);
    }

    public List<City> generateCountryReport(
            Connection connection,
            String countryCode) throws SQLException {

        validateScope(countryCode, "Country");
        return repository.findCitiesByCountry(connection, countryCode);
    }

    public List<City> generateDistrictReport(
            Connection connection,
            String district) throws SQLException {

        validateScope(district, "District");
        return repository.findCitiesByDistrict(connection, district);
    }

    public List<City> generateTopNWorldReport(
            Connection connection,
            int n) throws SQLException {

        validateLimit(n);
        return repository.findTopNWorldCities(connection, n);
    }
    public List<City> generateTopNContinentReport(
            Connection connection,
            String continent,
            int limit) throws SQLException {

        validateScope(continent, "Continent");
        validateLimit(limit);

        return repository.findTopNContinentCities(
                connection,
                continent,
                limit
        );
    }

    public List<City> generateTopNRegionReport(
            Connection connection,
            String region,
            int limit) throws SQLException {

        validateScope(region, "Region");
        validateLimit(limit);

        return repository.findTopNRegionCities(
                connection,
                region,
                limit
        );
    }

}