package org.napier.com.repository;

import org.napier.com.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityRepository {

    private static final String FIND_ALL_CITIES = """
            SELECT city.Name AS Name, country.Name AS Country,
                   city.District, city.Population
            FROM city JOIN country ON city.CountryCode = country.Code
            ORDER BY city.Population DESC
            """;

    private static final String FIND_CITIES_BY_CONTINENT = """
            SELECT city.Name AS Name, country.Name AS Country,
                   city.District, city.Population
            FROM city JOIN country ON city.CountryCode = country.Code
            WHERE country.Continent = ?
            ORDER BY city.Population DESC
            """;

    public List<City> findAllCities(Connection connection)
            throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(FIND_ALL_CITIES)) {
            return mapCities(statement);
        }
    }

    public List<City> findCitiesByContinent(
            Connection connection,
            String continent) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(FIND_CITIES_BY_CONTINENT)) {

            statement.setString(1, continent);
            return mapCities(statement);
        }
    }

    private List<City> mapCities(PreparedStatement statement)
            throws SQLException {

        List<City> cities = new ArrayList<>();

        try (ResultSet results = statement.executeQuery()) {
            while (results.next()) {
                cities.add(new City(
                        results.getString("Name"),
                        results.getString("Country"),
                        results.getString("District"),
                        results.getLong("Population")
                ));
            }
        }

        return cities;
    }
}