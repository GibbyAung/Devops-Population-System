package org.napier.com;

import org.napier.com.database.DatabaseConnection;
import org.napier.com.model.Country;
import org.napier.com.report.ReportFormatter;
import org.napier.com.repository.CountryRepository;
import org.napier.com.service.CountryReportService;

import java.sql.SQLException;
import java.util.List;

public class Main {

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

            /*
             * Requirement 3 - Region Country Report
             *
             * Example:
             * java -jar app.jar region "Eastern Asia"
             */
            if (args.length >= 2
                    && args[0].equalsIgnoreCase("region")) {

                String region = args[1];

                System.out.println(
                        "Countries in region: " + region
                );

                List<Country> countries =
                        service.generateRegionReport(
                                database.getConnection(),
                                region
                        );

                formatter.printCountries(countries);
            }

            /*
             * Requirement 2 - Continent Country Report
             *
             * Example:
             * java -jar app.jar Asia
             */
            else if (args.length >= 1) {

                String continent = args[0];

                System.out.println(
                        "Countries in continent: " + continent
                );

                List<Country> countries =
                        service.generateContinentReport(
                                database.getConnection(),
                                continent
                        );

                formatter.printCountries(countries);
            }

            /*
             * Requirement 1 - World Country Report
             *
             * Example:
             * java -jar app.jar
             */
            else {

                System.out.println(
                        "All countries in the world"
                );

                List<Country> countries =
                        service.generateWorldReport(
                                database.getConnection()
                        );

                formatter.printCountries(countries);
            }

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