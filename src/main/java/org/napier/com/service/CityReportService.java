package org.napier.com.service;

import org.napier.com.model.City;
import org.napier.com.repository.CityRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Validates city-report requests and delegates them to the repository.
 */
public class CityReportService {
    private final CityRepository repository;

    /**
     * Creates the service with its city data repository.
     */
    public CityReportService(CityRepository repository) {
        this.repository = repository;
    }

    /**
     * Generates the worldwide city report.
     *
     * @return all cities ordered by descending population
     */
    public List<City> generateWorldReport(Connection connection)
            throws SQLException {
        return repository.findAllCities(connection);
    }

    /**
     * Generates a city report for one continent.
     *
     * @return matching cities ordered by descending population
     */
    public List<City> generateContinentReport(
            Connection connection,
            String continent) throws SQLException {

        validateScope(continent, "Continent");
        return repository.findCitiesByContinent(connection, continent);
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
     * Generates a city report for one region.
     *
     * @return matching cities ordered by descending population
     */
    public List<City> generateRegionReport(
            Connection connection,
            String region) throws SQLException {

        validateScope(region, "Region");
        return repository.findCitiesByRegion(connection, region);
    }

    /**
     * Generates a city report for one country.
     *
     * @return matching cities ordered by descending population
     */
    public List<City> generateCountryReport(
            Connection connection,
            String countryCode) throws SQLException {

        validateScope(countryCode, "Country");
        return repository.findCitiesByCountry(connection, countryCode);
    }

    /**
     * Generates a city report for one district.
     *
     * @return matching cities ordered by descending population
     */
    public List<City> generateDistrictReport(
            Connection connection,
            String district) throws SQLException {

        validateScope(district, "District");
        return repository.findCitiesByDistrict(connection, district);
    }

    /**
     * Generates a Top-N worldwide city report.
     *
     * @return up to N cities ordered by descending population
     */
    public List<City> generateTopNWorldReport(
            Connection connection,
            int n) throws SQLException {

        validateLimit(n);
        return repository.findTopNWorldCities(connection, n);
    }
    /**
     * Generates a Top-N city report for one continent.
     *
     * @return up to N matching cities ordered by descending population
     */
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

    /**
     * Generates a Top-N city report for one region.
     *
     * @return up to N matching cities ordered by descending population
     */
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

    /**
     * Generates a Top-N city report for one country.
     *
     * @return up to N matching cities ordered by descending population
     */
    public List<City> generateTopNCountryReport(
            Connection connection,
            String countryCode,
            int limit) throws SQLException {

        validateScope(countryCode, "Country");
        validateLimit(limit);
        return repository.findTopNCountryCities(connection, countryCode, limit);
    }

    /**
     * Generates a Top-N city report for one district.
     *
     * @return up to N matching cities ordered by descending population
     */
    public List<City> generateTopNDistrictReport(
            Connection connection,
            String district,
            int limit) throws SQLException {

        validateScope(district, "District");
        validateLimit(limit);
        return repository.findTopNDistrictCities(connection, district, limit);
    }

}
