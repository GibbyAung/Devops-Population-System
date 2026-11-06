package org.napier.com;

import org.napier.com.database.DatabaseConnection;
import org.napier.com.model.Country;
import org.napier.com.report.ReportFormatter;
import org.napier.com.repository.CountryRepository;
import org.napier.com.service.CountryReportService;
import org.napier.com.model.City;
import org.napier.com.repository.CityRepository;
import org.napier.com.service.CityReportService;
import org.napier.com.model.PopulationBreakdown;
import org.napier.com.repository.PopulationBreakdownRepository;
import org.napier.com.service.PopulationReportService;
import org.napier.com.model.PopulationTotal;
import org.napier.com.repository.PopulationTotalRepository;
import org.napier.com.service.PopulationTotalService;

import java.sql.SQLException;
import java.util.List;

/**
 * Command-line entry point for country and city population reports.
 */
public class Main {

    /**
     * Parses and validates a positive whole-number command argument.
     *
     * @return parsed positive integer
     */
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

    /**
     * Connects to the database and runs the report selected by the arguments.
     */
    public static void main(String[] args) {

        DatabaseConnection database = new DatabaseConnection();
        database.connect();

        if (database.getConnection() == null) {
            System.err.println(
                    "Cannot run report because the database connection failed."
            );
            System.exit(1);
        }

        int exitCode = 0;

        try {
            CountryReportService service =
                    new CountryReportService(
                            new CountryRepository()
                    );

            ReportFormatter formatter =
                    new ReportFormatter();

            CityReportService cityService =
                    new CityReportService(new CityRepository());

            PopulationReportService populationReportService =
                    new PopulationReportService(
                            new PopulationBreakdownRepository()
                    );

            PopulationTotalService populationTotalService =
                    new PopulationTotalService(
                            new PopulationTotalRepository()
                    );

            // Requirement 25 - Population breakdown by country
            // Example: java -jar app.jar country-population
            if (args.length >= 1
                    && args[0].equalsIgnoreCase("country-population")) {

                if (args.length != 1) {
                    throw new IllegalArgumentException(
                            "Usage: country-population"
                    );
                }

                List<PopulationBreakdown> breakdowns =
                        populationReportService.generateCountryReport(
                                database.getConnection()
                        );

                formatter.printCountryPopulationBreakdowns(
                        "Population breakdown by country",
                        breakdowns
                );
                return;
            }

            // Requirement 26 - World total population
            // Example: java -jar app.jar world-population
            if (args.length >= 1
                    && args[0].equalsIgnoreCase("world-population")) {

                if (args.length != 1) {
                    throw new IllegalArgumentException(
                            "Usage: world-population"
                    );
                }

                long totalPopulation =
                        populationReportService.generateWorldTotalPopulation(
                                database.getConnection()
                        );

                formatter.printWorldTotalPopulation(totalPopulation);
                return;
            }

            // Requirement 23 - Population breakdown by continent
            // Example: java -jar app.jar continent-population
            if (args.length >= 1
                    && args[0].equalsIgnoreCase("continent-population")) {

                if (args.length != 1) {
                    throw new IllegalArgumentException(
                            "Usage: continent-population"
                    );
                }

                List<PopulationBreakdown> breakdowns =
                        populationReportService.generateContinentReport(
                                database.getConnection()
                        );

                formatter.printContinentPopulationBreakdowns(
                        "Population breakdown by continent",
                        breakdowns
                );
                return;
            }

            // Requirement 24 - Population breakdown by region
            // Example: java -jar app.jar region-population
            if (args.length >= 1
                    && args[0].equalsIgnoreCase("region-population")) {

                if (args.length != 1) {
                    throw new IllegalArgumentException(
                            "Usage: region-population"
                    );
                }

                List<PopulationBreakdown> breakdowns =
                        populationReportService.generateRegionReport(
                                database.getConnection()
                        );

                formatter.printRegionPopulationBreakdowns(
                        "Population breakdown by region",
                        breakdowns
                );
                return;
            }

            // Requirement 27 - Total population of one continent
// Example: java -jar app.jar continent-total-population Asia
            if (args.length >= 1
                    && args[0].equalsIgnoreCase("continent-total-population")) {

                if (args.length != 2) {
                    throw new IllegalArgumentException(
                            "Usage: continent-total-population <continent>"
                    );
                }

                PopulationTotal total =
                        populationTotalService.generateContinentReport(
                                database.getConnection(),
                                args[1]
                        );

                formatter.printPopulationTotal("Continent", total);
                return;
            }

// Requirement 28 - Total population of one region
// Example: java -jar app.jar region-total-population "Southeast Asia"
            if (args.length >= 1
                    && args[0].equalsIgnoreCase("region-total-population")) {

                if (args.length != 2) {
                    throw new IllegalArgumentException(
                            "Usage: region-total-population <region>"
                    );
                }

                PopulationTotal total =
                        populationTotalService.generateRegionReport(
                                database.getConnection(),
                                args[1]
                        );

                formatter.printPopulationTotal("Region", total);
                return;
            }

            // Requirement 29 - Total population of one country by full name
// Example: java -jar app.jar country-total-population "United Kingdom"
            if (args.length >= 1
                    && args[0].equalsIgnoreCase("country-total-population")) {

                if (args.length != 2) {
                    throw new IllegalArgumentException(
                            "Usage: country-total-population <countryName>"
                    );
                }

                PopulationTotal total =
                        populationTotalService.generateCountryReport(
                                database.getConnection(),
                                args[1]
                        );

                formatter.printPopulationTotal("Country", total);
                return;
            }

// Requirement 30 - Aggregated city population of one district
// Example: java -jar app.jar district-total-population California
            if (args.length >= 1
                    && args[0].equalsIgnoreCase("district-total-population")) {

                if (args.length != 2) {
                    throw new IllegalArgumentException(
                            "Usage: district-total-population <district>"
                    );
                }

                PopulationTotal total =
                        populationTotalService.generateDistrictReport(
                                database.getConnection(),
                                args[1]
                        );

                formatter.printPopulationTotal("District", total);
                return;
            }

            // Requirement 31 - Total population of a city
// Example: java -jar app.jar city-total-population "Yangon"
            if (args.length >= 1
                    && args[0].equalsIgnoreCase("city-total-population")) {

                if (args.length != 2) {
                    throw new IllegalArgumentException(
                            "Usage: city-total-population <cityName>"
                    );
                }

                PopulationTotal total =
                        populationTotalService.generateCityReport(
                                database.getConnection(),
                                args[1]
                        );

                formatter.printPopulationTotal("City", total);
                return;
            }

            // Requirement 15 - Top N populated cities in a country (ISO code)
            // Example: java -jar app.jar city-top-country CHN 10
            if (args.length >= 1
                    && args[0].equalsIgnoreCase("city-top-country")) {

                if (args.length != 3) {
                    throw new IllegalArgumentException(
                            "Country code and N must be provided. "
                                    + "Usage: city-top-country <countryCode> <N>"
                    );
                }

                String countryCode = args[1];
                int limit = parsePositiveInteger(args[2]);

                List<City> cities =
                        cityService.generateTopNCountryReport(
                                database.getConnection(),
                                countryCode,
                                limit
                        );

                formatter.printCities(
                        "Top " + limit
                                + " populated cities in country: " + countryCode,
                        cities
                );
                return;
            }

            // Requirement 16 - Top N populated cities in a district
            // Example: java -jar app.jar city-top-district California 10
            if (args.length >= 1
                    && args[0].equalsIgnoreCase("city-top-district")) {

                if (args.length != 3) {
                    throw new IllegalArgumentException(
                            "District and N must be provided. "
                                    + "Usage: city-top-district <district> <N>"
                    );
                }

                String district = args[1];
                int limit = parsePositiveInteger(args[2]);

                List<City> cities =
                        cityService.generateTopNDistrictReport(
                                database.getConnection(),
                                district,
                                limit
                        );

                formatter.printCities(
                        "Top " + limit
                                + " populated cities in district: " + district,
                        cities
                );
                return;
            }

            // Requirement 9 - Cities in region
// Example: java -jar app.jar city-region "Eastern Asia"
            if (args.length >= 2
                    && args[0].equalsIgnoreCase("city-region")) {

                String region = args[1];

                List<City> cities =
                        cityService.generateRegionReport(
                                database.getConnection(),
                                region
                        );

                formatter.printCities(
                        "Cities in region: " + region,
                        cities
                );
                return;
            }

// Requirement 10 - Cities in country (use ISO code)
// Example: java -jar app.jar city-country CHN
            if (args.length >= 2
                    && args[0].equalsIgnoreCase("city-country")) {

                String countryCode = args[1];

                List<City> cities =
                        cityService.generateCountryReport(
                                database.getConnection(),
                                countryCode
                        );

                formatter.printCities(
                        "Cities in country: " + countryCode,
                        cities
                );
                return;
            }

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

            // Requirement 11 - All cities in a district
            // Example: java -jar app.jar city-district California
            if (args.length >= 1
                    && args[0].equalsIgnoreCase("city-district")) {

                if (args.length != 2) {
                    throw new IllegalArgumentException(
                            "District must be provided. Usage: city-district <district>"
                    );
                }

                String district = args[1];

                List<City> cities =
                        cityService.generateDistrictReport(
                                database.getConnection(),
                                district
                        );

                formatter.printCities(
                        "Cities in district: " + district,
                        cities
                );
                return;
            }

            // Requirement 12 - Top N populated cities in the world
            // Example: java -jar app.jar city-top-world 10
            if (args.length >= 1
                    && args[0].equalsIgnoreCase("city-top-world")) {

                if (args.length != 2) {
                    throw new IllegalArgumentException(
                            "N must be provided. Usage: city-top-world <N>"
                    );
                }

                int limit = parsePositiveInteger(args[1]);

                List<City> cities =
                        cityService.generateTopNWorldReport(
                                database.getConnection(),
                                limit
                        );

                formatter.printCities(
                        "Top " + limit + " populated cities in the world",
                        cities
                );
                return;
            }

            // Requirement 13 - Top N populated cities in a continent
            // Example: java -jar app.jar city-top-continent Asia 10
            if (args.length >= 1
                    && args[0].equalsIgnoreCase("city-top-continent")) {

                if (args.length != 3) {
                    throw new IllegalArgumentException(
                            "Continent and N must be provided. "
                                    + "Usage: city-top-continent <continent> <N>"
                    );
                }

                String continent = args[1];
                int limit = parsePositiveInteger(args[2]);

                List<City> cities =
                        cityService.generateTopNContinentReport(
                                database.getConnection(),
                                continent,
                                limit
                        );

                formatter.printCities(
                        "Top " + limit
                                + " populated cities in continent: "
                                + continent,
                        cities
                );
                return;
            }

            // Requirement 14 - Top N populated cities in a region
            // Example: java -jar app.jar city-top-region "Eastern Asia" 10
            if (args.length >= 1
                    && args[0].equalsIgnoreCase("city-top-region")) {

                if (args.length != 3) {
                    throw new IllegalArgumentException(
                            "Region and N must be provided. "
                                    + "Usage: city-top-region <region> <N>"
                    );
                }

                String region = args[1];
                int limit = parsePositiveInteger(args[2]);

                List<City> cities =
                        cityService.generateTopNRegionReport(
                                database.getConnection(),
                                region,
                                limit
                        );

                formatter.printCities(
                        "Top " + limit
                                + " populated cities in region: "
                                + region,
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
                    "Report failed: " + exception.getMessage()
            );
            exitCode = 1;

        } catch (SQLException exception) {

            System.err.println(
                    "Database report failed: " + exception.getMessage()
            );
            exitCode = 1;

        } finally {
            database.disconnect();
        }

        // ---> THIS GOES RIGHT AFTER FINALLY, INSIDE THE MAIN METHOD:
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }
}
