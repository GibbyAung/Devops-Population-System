package org.napier.com.repository;

import org.napier.com.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryRepository {
    private static final String FIND_ALL_COUNTRIES = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
                   capital.Name AS Capital
            FROM country c
            LEFT JOIN city capital ON c.Capital = capital.ID
            ORDER BY c.Population DESC
            """;

    public List<Country> findAllCountries(Connection connection) throws SQLException {
        List<Country> countries = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_COUNTRIES);
             ResultSet results = statement.executeQuery()) {
            while (results.next()) {
                String capital = results.getString("Capital");
                if (capital == null || capital.isBlank()) {
                    capital = "Unknown";
                }

                countries.add(new Country(
                        results.getString("Code"),
                        results.getString("Name"),
                        results.getString("Continent"),
                        results.getString("Region"),
                        results.getLong("Population"),
                        capital));
            }
        }
        return countries;
    }
}