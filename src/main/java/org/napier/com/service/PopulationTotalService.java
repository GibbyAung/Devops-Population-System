package org.napier.com.service;

import org.napier.com.model.PopulationTotal;
import org.napier.com.repository.PopulationTotalRepository;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Validates requests for continent and region population totals.
 */
public class PopulationTotalService {

    private final PopulationTotalRepository repository;

    public PopulationTotalService(
            PopulationTotalRepository repository) {
        this.repository = repository;
    }

    public PopulationTotal generateContinentReport(
            Connection connection,
            String continent) throws SQLException {

        String name = validateName(continent, "Continent");

        return repository.findTotalByContinent(connection, name)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown continent: " + name
                ));
    }

    public PopulationTotal generateRegionReport(
            Connection connection,
            String region) throws SQLException {

        String name = validateName(region, "Region");

        return repository.findTotalByRegion(connection, name)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown region: " + name
                ));
    }

    public PopulationTotal generateCountryReport(
            Connection connection,
            String countryName) throws SQLException {

        String name = validateName(countryName, "Country");

        return repository.findTotalByCountry(connection, name)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown country: " + name
                ));
    }

    public PopulationTotal generateDistrictReport(
            Connection connection,
            String district) throws SQLException {

        String name = validateName(district, "District");

        return repository.findTotalByDistrict(connection, name)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown district: " + name
                ));
    }

    private String validateName(String value, String label) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    label + " must be provided."
            );
        }

        return value.strip();
    }
}