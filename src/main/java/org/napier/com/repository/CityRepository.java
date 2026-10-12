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
    private static final String FIND_CITIES_BY_REGION = """
        SELECT city.Name AS Name, country.Name AS Country,
               city.District, city.Population
        FROM city JOIN country ON city.CountryCode = country.Code
        WHERE country.Region = ?
        ORDER BY city.Population DESC
        """;

    private static final String FIND_CITIES_BY_COUNTRY = """
        SELECT city.Name AS Name, country.Name AS Country,
               city.District, city.Population
        FROM city JOIN country ON city.CountryCode = country.Code
        WHERE city.CountryCode = ?
        ORDER BY city.Population DESC
        """;
    private static final String FIND_CITIES_BY_DISTRICT = """
        SELECT city.Name AS Name, country.Name AS Country,
               city.District, city.Population
        FROM city JOIN country ON city.CountryCode = country.Code
        WHERE city.District = ?
        ORDER BY city.Population DESC
        """;

    private static final String FIND_TOP_N_WORLD_CITIES = """
        SELECT city.Name AS Name, country.Name AS Country,
               city.District, city.Population
        FROM city JOIN country ON city.CountryCode = country.Code
        ORDER BY city.Population DESC
        LIMIT ?
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
    public List<City> findCitiesByRegion(
            Connection connection,
            String region) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(FIND_CITIES_BY_REGION)) {

            statement.setString(1, region);
            return mapCities(statement);
        }
    }

    public List<City> findCitiesByCountry(
            Connection connection,
            String countryCode) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(FIND_CITIES_BY_COUNTRY)) {

            statement.setString(1, countryCode);
            return mapCities(statement);
        }
    }
    public List<City> findCitiesByDistrict(
            Connection connection,
            String district) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(FIND_CITIES_BY_DISTRICT)) {

            statement.setString(1, district);
            return mapCities(statement);
        }
    }

    public List<City> findTopNWorldCities(
            Connection connection,
            int n) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(FIND_TOP_N_WORLD_CITIES)) {

            statement.setInt(1, n);
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
