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

    public List<Country> generateWorldReport(Connection connection) throws SQLException {
        return repository.findAllCountries(connection);
    }
}