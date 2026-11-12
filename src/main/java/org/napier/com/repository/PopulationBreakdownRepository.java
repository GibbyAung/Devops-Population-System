package org.napier.com.repository;

import org.napier.com.model.PopulationBreakdown;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Executes aggregated population-breakdown queries against the world database.
 */
public class PopulationBreakdownRepository {

    private static final String FIND_POPULATION_BREAKDOWN_BY_COUNTRY = """
            SELECT co.Name AS Name,
                   co.Population AS TotalPopulation,
                   COALESCE(SUM(ci.Population), 0) AS CityPopulation
            FROM country co
            LEFT JOIN city ci ON ci.CountryCode = co.Code
            GROUP BY co.Code, co.Name, co.Population
            ORDER BY co.Population DESC
            """;

    private static final String FIND_WORLD_TOTAL_POPULATION = """
            SELECT COALESCE(SUM(Population), 0) AS TotalPopulation
            FROM country
            """;

    private static final String FIND_POPULATION_BREAKDOWN_BY_CONTINENT = """
            SELECT co.Continent AS Name,
                   SUM(co.Population) AS TotalPopulation,
                   COALESCE(ct.CityPopulation, 0) AS CityPopulation
            FROM country co
            LEFT JOIN (
                SELECT c2.Continent, SUM(c.Population) AS CityPopulation
                FROM city c
                JOIN country c2 ON c.CountryCode = c2.Code
                GROUP BY c2.Continent
            ) ct ON ct.Continent = co.Continent
            GROUP BY co.Continent
            ORDER BY TotalPopulation DESC
            """;

    private static final String FIND_POPULATION_BREAKDOWN_BY_REGION = """
            SELECT co.Region AS Name,
                   SUM(co.Population) AS TotalPopulation,
                   COALESCE(ct.CityPopulation, 0) AS CityPopulation
            FROM country co
            LEFT JOIN (
                SELECT c2.Region, SUM(c.Population) AS CityPopulation
                FROM city c
                JOIN country c2 ON c.CountryCode = c2.Code
                GROUP BY c2.Region
            ) ct ON ct.Region = co.Region
            GROUP BY co.Region
            ORDER BY TotalPopulation DESC
            """;

    /**
     * Finds the population breakdown for every country, including countries
     * without a matching city row.
     *
     * @return country rows ordered by descending total population
     */
    public List<PopulationBreakdown> findPopulationBreakdownByCountry(
            Connection connection) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(
                FIND_POPULATION_BREAKDOWN_BY_COUNTRY)) {
            return mapPopulationBreakdowns(statement);
        }
    }

    /**
     * Finds the total population across all countries.
     *
     * @return world population as a 64-bit value
     */
    public long findWorldTotalPopulation(Connection connection)
            throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(
                FIND_WORLD_TOTAL_POPULATION);
             ResultSet results = statement.executeQuery()) {
            return results.next() ? results.getLong("TotalPopulation") : 0L;
        }
    }

    /**
     * Finds the population breakdown for every continent.
     *
     * @return continent rows ordered by descending total population
     */
    public List<PopulationBreakdown> findPopulationBreakdownByContinent(
            Connection connection) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             FIND_POPULATION_BREAKDOWN_BY_CONTINENT)) {
            return mapPopulationBreakdowns(statement);
        }
    }

    /**
     * Finds the population breakdown for every region.
     *
     * @return region rows ordered by descending total population
     */
    public List<PopulationBreakdown> findPopulationBreakdownByRegion(
            Connection connection) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             FIND_POPULATION_BREAKDOWN_BY_REGION)) {
            return mapPopulationBreakdowns(statement);
        }
    }

    /**
     * Executes a prepared breakdown query and maps each result row.
     *
     * @return population breakdown rows returned by the query
     */
    private List<PopulationBreakdown> mapPopulationBreakdowns(
            PreparedStatement statement) throws SQLException {

        List<PopulationBreakdown> breakdowns = new ArrayList<>();

        try (ResultSet results = statement.executeQuery()) {
            while (results.next()) {
                breakdowns.add(new PopulationBreakdown(
                        results.getString("Name"),
                        results.getLong("TotalPopulation"),
                        results.getLong("CityPopulation")
                ));
            }
        }

        return breakdowns;
    }
}
