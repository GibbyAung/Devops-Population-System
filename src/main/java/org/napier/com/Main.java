package org.napier.com;

import org.napier.com.database.DatabaseConnection;
import org.napier.com.model.Country;
import org.napier.com.report.ReportFormatter;
import org.napier.com.repository.CountryRepository;
import org.napier.com.service.CountryReportService;
import org.napier.com.model.City;
import org.napier.com.repository.CityRepository;
import org.napier.com.service.CityReportService;

import java.sql.SQLException;
import java.util.List;

public class Main {

    private static int parsePositiveInteger(String value) {
        try {
            int result = Integer.parseInt(value);

            if (result <= 0) {
                throw new IllegalArgumentException(
                        "N must be a positive whole number."
                );
            }

            return result;

        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "N must be a positive whole number.",
                    exception
            );
        }
    }

    public static void main(String[] args) {

        DatabaseConnection database = new DatabaseConnection();
        database.connect();

        if (database.getConnection() == null) {
            System.err.println(
                    "Cannot run report because the database connection failed."
            );
            System.exit(1);
        }

        try {
            CountryReportService service =
                    new CountryReportService(
                            new CountryRepository()
                    );

            ReportFormatter formatter =
                    new ReportFormatter();

            CityReportService cityService =
                    new CityReportService(new CityRepository());

// Requirement 7 - All cities in world
// Example: java -jar app.jar city-world
            if (args.length >= 1
                    && args[0].equalsIgnoreCase("city-world")) {

                List<City> cities =
                        cityService.generateWorldReport(
                                database.getConnection()
                        );

                formatter.printCities(
                        "All cities in the world",
                        cities
                );
                return;
            }

// Requirement 8 - Cities in continent
// Example: java -jar app.jar city-continent Asia
            if (args.length >= 2
                    && args[0].equalsIgnoreCase("city-continent")) {

                String continent = args[1];

                List<City> cities =
                        cityService.generateContinentReport(
                                database.getConnection(),
                                continent
                        );

                formatter.printCities(
                        "Cities in continent: " + continent,
                        cities
                );
                return;
            }

            /*
             * Requirement 5, 6
             */

            if (args.length >= 1
                    && args[0].equalsIgnoreCase("continent-top-n")) {

                if (args.length < 3) {
                    throw new IllegalArgumentException(
                            "A continent and positive N are required."
                    );
                }

                String continent = args[1];
                int limit = parsePositiveInteger(args[2]);

                List<Country> countries =
                        service.generateContinentTopNReport(
                                database.getConnection(),
                                continent,
                                limit
                        );

                formatter.printCountries(
                        "Top " + limit
                                + " countries in continent: "
                                + continent,
                        countries
                );
            }

            else if (args.length >= 1
                    && args[0].equalsIgnoreCase("region-top-n")) {

                if (args.length < 3) {
                    throw new IllegalArgumentException(
                            "A region and positive N are required."
                    );
                }

                String region = args[1];
                int limit = parsePositiveInteger(args[2]);

                List<Country> countries =
                        service.generateRegionTopNReport(
                                database.getConnection(),
                                region,
                                limit
                        );

                formatter.printCountries(
                        "Top " + limit
                                + " countries in region: "
                                + region,
                        countries
                );
            }

            /*
             * Requirement 4 - Top N Country Report
             */

            if (args.length >= 1
                    && args[0].equalsIgnoreCase("top-n")) {

                if (args.length < 2) {
                    throw new IllegalArgumentException(
                            "N must be provided as a positive whole number."
                    );
                }

                int limit = parsePositiveInteger(args[1]);

                List<Country> countries =
                        service.generateWorldTopNReport(
                                database.getConnection(),
                                limit
                        );

                formatter.printCountries(
                        "Top " + limit + " countries in the world",
                        countries
                );
            }

            /*
             * Requirement 3 - Region Country Report
             *
             * Example:
             * java -jar app.jar region "Eastern Asia"
             */
            if (args.length >= 2
                    && args[0].equalsIgnoreCase("region")) {

                String region = args[1];

                List<Country> countries =
                        service.generateRegionReport(
                                database.getConnection(),
                                region
                        );

                formatter.printCountries(
                        "Countries in region: " + region,
                        countries
                );
            }

            /*
             * Requirement 2 - Continent Country Report
             *
             * Example:
             * java -jar app.jar Asia
             */
            else if (args.length >= 1) {

                String continent = args[0];

                List<Country> countries =
                        service.generateContinentReport(
                                database.getConnection(),
                                continent
                        );

                formatter.printCountries(
                        "Countries in continent: " + continent,
                        countries
                );
            }

            /*
             * Requirement 1 - World Country Report
             *
             * Example:
             * java -jar app.jar
             */
            else {

                List<Country> countries =
                        service.generateWorldReport(
                                database.getConnection()
                        );

                formatter.printCountries(
                        "All countries in the world",
                        countries
                );
            }


        } catch (IllegalArgumentException exception) {

            System.err.println(
                    "Top-N country report failed: "
                            + exception.getMessage()
            );

            System.exit(1);

        } catch (SQLException exception) {

            System.err.println(
                    "Country report failed: "
                            + exception.getMessage()
            );

            System.exit(1);

        } finally {
            database.disconnect();
        }
    }
}