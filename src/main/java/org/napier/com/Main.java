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
                    new CountryReportService(new CountryRepository());

            List<Country> countries =
                    service.generateWorldReport(database.getConnection());

            new ReportFormatter().printCountries(countries);

        } catch (SQLException exception) {
            System.err.println(
                    "Country report failed: " + exception.getMessage()
            );
            System.exit(1);

        } finally {
            database.disconnect();
        }
    }
}