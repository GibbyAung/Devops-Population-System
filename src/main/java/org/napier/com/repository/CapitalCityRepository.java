package org.napier.com.repository;

import org.napier.com.model.CapitalCity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Executes parameterized capital-city queries against the world database.
 */
public class CapitalCityRepository {

    private static final String FIND_ALL_CAPITAL_CITIES = """
            SELECT city.Name AS Name,
                   country.Name AS Country,
                   city.Population AS Population
            FROM country
            JOIN city ON country.Capital = city.ID
            ORDER BY city.Population DESC
            """;

    private static final String FIND_CAPITAL_CITIES_BY_CONTINENT = """
            SELECT city.Name AS Name,
                   country.Name AS Country,
                   city.Population AS Population
            FROM country
            JOIN city ON country.Capital = city.ID
            WHERE country.Continent = ?
            ORDER BY city.Population DESC
            """;

    private static final String FIND_CAPITAL_CITIES_BY_REGION = """
        SELECT city.Name AS Name,
               country.Name AS Country,
               city.Population AS Population
        FROM country
        JOIN city ON country.Capital = city.ID
        WHERE country.Region = ?
        ORDER BY city.Population DESC
        """;

    /**
     * Finds every capital city ordered by descending population.
     *
     * @return capital cities ordered by descending population
     */
    public List<CapitalCity> findAllCapitalCities(Connection connection)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(FIND_ALL_CAPITAL_CITIES)) {
            return mapCapitalCities(statement);
        }
    }

    /**
     * Finds capital cities in a continent ordered by descending population.
     *
     * @return matching capital cities ordered by descending population
     */
    public List<CapitalCity> findCapitalCitiesByContinent(
            Connection connection,
            String continent) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             FIND_CAPITAL_CITIES_BY_CONTINENT)) {

            statement.setString(1, continent);
            return mapCapitalCities(statement);
        }
    }

    /**
     * Executes a prepared query and maps its capital-city rows.
     *
     * @return capital-city rows returned by the query
     */

    public List<CapitalCity> findCapitalCitiesByRegion(
            Connection connection,
            String region) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             FIND_CAPITAL_CITIES_BY_REGION)) {

            statement.setString(1, region);
            return mapCapitalCities(statement);
        }
    }

    private List<CapitalCity> mapCapitalCities(
            PreparedStatement statement) throws SQLException {

        List<CapitalCity> capitalCities = new ArrayList<>();

        try (ResultSet results = statement.executeQuery()) {
            while (results.next()) {
                capitalCities.add(new CapitalCity(
                        results.getString("Name"),
                        results.getString("Country"),
                        results.getLong("Population")
                ));
            }
        }

        return capitalCities;
    }
}
