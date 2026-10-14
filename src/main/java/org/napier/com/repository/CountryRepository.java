package org.napier.com.repository;

import org.napier.com.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Executes parameterized country-report queries against the world database.
 */
public class CountryRepository {

    private static final String FIND_ALL_COUNTRIES = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
                   capital.Name AS Capital
            FROM country c
            LEFT JOIN city capital ON c.Capital = capital.ID
            ORDER BY c.Population DESC
            """;

    private static final String FIND_COUNTRIES_BY_CONTINENT = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
                   capital.Name AS Capital
            FROM country c
            LEFT JOIN city capital ON c.Capital = capital.ID
            WHERE c.Continent = ?
            ORDER BY c.Population DESC
            """;

    private static final String FIND_COUNTRIES_BY_REGION = """
        SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
               capital.Name AS Capital
        FROM country c
        LEFT JOIN city capital ON c.Capital = capital.ID
        WHERE c.Region = ?
        ORDER BY c.Population DESC
        """;

    private static final String FIND_TOP_N_COUNTRIES = """
        SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
               capital.Name AS Capital
        FROM country c
        LEFT JOIN city capital ON c.Capital = capital.ID
        ORDER BY c.Population DESC
        LIMIT ?
        """;

    private static final String FIND_TOP_N_COUNTRIES_BY_CONTINENT = """
        SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
               capital.Name AS Capital
        FROM country c
        LEFT JOIN city capital ON c.Capital = capital.ID
        WHERE c.Continent = ?
        ORDER BY c.Population DESC
        LIMIT ?
        """;

    private static final String FIND_TOP_N_COUNTRIES_BY_REGION = """
        SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
               capital.Name AS Capital
        FROM country c
        LEFT JOIN city capital ON c.Capital = capital.ID
        WHERE c.Region = ?
        ORDER BY c.Population DESC
        LIMIT ?
        """;

    /**
     * Finds every country ordered by population from largest to smallest.
     *
     * @return countries ordered by descending population
     */
    public List<Country> findAllCountries(Connection connection)
            throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(FIND_ALL_COUNTRIES)) {
            return mapCountries(statement);
        }
    }

    /**
     * Finds countries in a continent ordered by descending population.
     *
     * @return matching countries ordered by descending population
     */
    public List<Country> findCountriesByContinent(
            Connection connection,
            String continent) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(FIND_COUNTRIES_BY_CONTINENT)) {

            statement.setString(1, continent);
            return mapCountries(statement);
        }
    }

    /**
     * Finds countries in a region ordered by descending population.
     *
     * @return matching countries ordered by descending population
     */
    public List<Country> findCountriesByRegion(
            Connection connection,
            String region) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(FIND_COUNTRIES_BY_REGION)) {

            statement.setString(1, region);
            return mapCountries(statement);
        }
    }

    /**
     * Finds up to N of the world's most populated countries.
     *
     * @return countries ordered by descending population
     */
    public List<Country> findTopNCountries(
            Connection connection,
            int limit) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(FIND_TOP_N_COUNTRIES)) {

            statement.setInt(1, limit);
            return mapCountries(statement);
        }
    }

    /**
     * Finds up to N of the most populated countries in a continent.
     *
     * @return matching countries ordered by descending population
     */
    public List<Country> findTopNCountriesByContinent(
            Connection connection,
            String continent,
            int limit) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             FIND_TOP_N_COUNTRIES_BY_CONTINENT)) {

            statement.setString(1, continent);
            statement.setInt(2, limit);
            return mapCountries(statement);
        }
    }

    /**
     * Finds up to N of the most populated countries in a region.
     *
     * @return matching countries ordered by descending population
     */
    public List<Country> findTopNCountriesByRegion(
            Connection connection,
            String region,
            int limit) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             FIND_TOP_N_COUNTRIES_BY_REGION)) {

            statement.setString(1, region);
            statement.setInt(2, limit);
            return mapCountries(statement);
        }
    }

    /**
     * Executes a prepared country query and maps each result row.
     *
     * @return country rows returned by the query
     */
    private List<Country> mapCountries(PreparedStatement statement)
            throws SQLException {

        List<Country> countries = new ArrayList<>();

        try (ResultSet results = statement.executeQuery()) {
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
                        capital
                ));
            }
        }

        return countries;
    }
}
