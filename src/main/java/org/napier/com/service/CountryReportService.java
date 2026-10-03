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
}