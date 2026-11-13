package org.napier.com.repository;

import org.napier.com.model.PopulationTotal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Retrieves total populations from the world country table.
 */
public class PopulationTotalRepository {

    private static final String FIND_TOTAL_BY_CONTINENT = """
            SELECT Continent AS Name,
                   CAST(SUM(Population) AS SIGNED) AS TotalPopulation
            FROM country
            WHERE Continent = ?
            GROUP BY Continent
            """;

    private static final String FIND_TOTAL_BY_REGION = """
            SELECT Region AS Name,
                   CAST(SUM(Population) AS SIGNED) AS TotalPopulation
            FROM country
            WHERE Region = ?
            GROUP BY Region
            """;

    private static final String FIND_TOTAL_BY_COUNTRY = """
        SELECT Name,
               CAST(Population AS SIGNED) AS TotalPopulation
        FROM country
        WHERE Name = ?
        """;

    private static final String FIND_TOTAL_BY_DISTRICT = """
        SELECT District AS Name,
               CAST(SUM(Population) AS SIGNED) AS TotalPopulation
        FROM city
        WHERE District = ?
        GROUP BY District
        """;

    private static final String FIND_TOTAL_BY_CITY = """
        SELECT Name,
               CAST(SUM(Population) AS SIGNED) AS TotalPopulation
        FROM city
        WHERE Name = ?
        GROUP BY Name
        """;

    public Optional<PopulationTotal> findTotalByContinent(
            Connection connection,
            String continent) throws SQLException {

        return findTotal(
                connection,
                FIND_TOTAL_BY_CONTINENT,
                continent
        );
    }

    public Optional<PopulationTotal> findTotalByRegion(
            Connection connection,
            String region) throws SQLException {

        return findTotal(
                connection,
                FIND_TOTAL_BY_REGION,
                region
        );
    }

    public Optional<PopulationTotal> findTotalByCountry(
            Connection connection,
            String countryName) throws SQLException {

        return findTotal(
                connection,
                FIND_TOTAL_BY_COUNTRY,
                countryName
        );
    }

    public Optional<PopulationTotal> findTotalByDistrict(
            Connection connection,
            String district) throws SQLException {

        return findTotal(
                connection,
                FIND_TOTAL_BY_DISTRICT,
                district
        );
    }

    public Optional<PopulationTotal> findTotalByCity(
            Connection connection,
            String cityName) throws SQLException {

        return findTotal(
                connection,
                FIND_TOTAL_BY_CITY,
                cityName
        );
    }

    private Optional<PopulationTotal> findTotal(
            Connection connection,
            String sql,
            String name) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);

            try (ResultSet results = statement.executeQuery()) {
                if (!results.next()) {
                    return Optional.empty();
                }

                return Optional.of(
                        new PopulationTotal(
                                results.getString("Name"),
                                results.getLong("TotalPopulation")
                        )
                );
            }
        }
    }
}