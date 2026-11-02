package org.napier.com.report;

import org.napier.com.model.Country;
import org.napier.com.model.City;
import org.napier.com.model.CapitalCity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Formats country and city report data as readable console tables.
 */
public class ReportFormatter {

    private static final String[] HEADERS = {
            "Code",
            "Name",
            "Continent",
            "Region",
            "Population",
            "Capital"
    };

    private static final int POPULATION_COLUMN = 4;

    private static final String[] CITY_HEADERS = {
            "Name",
            "Country",
            "District",
            "Population"
    };
    private static final int CITY_POPULATION_COLUMN = 3;

    /**
     * Prints a country report with the default report name.
     */
    public void printCountries(List<Country> countries) {
        printCountries("Countries", countries);
    }

    /**
     * Prints a named country report and its total population.
     */
    public void printCountries(String reportName, List<Country> countries) {
        System.out.println("Report for " + reportName);
        System.out.println();

        if (countries.isEmpty()) {
            System.out.println("No countries found.");
            System.out.println();
            return;
        }

        List<String[]> rows = new ArrayList<>();

        for (Country country : countries) {
            rows.add(toRow(country));
        }

        String[] totalRow = createTotalRow(countries);

        // Include total row when calculating column widths.
        List<String[]> rowsForWidthCalculation = new ArrayList<>(rows);
        rowsForWidthCalculation.add(totalRow);

        int[] columnWidths =
                calculateColumnWidths(rowsForWidthCalculation);

        // Header
        System.out.println(formatSeparator(columnWidths));
        System.out.println(formatRow(HEADERS, columnWidths));
        System.out.println(formatSeparator(columnWidths));

        // Country rows
        for (String[] row : rows) {
            System.out.println(formatRow(row, columnWidths));
        }

        // Total row
        System.out.println(formatSeparator(columnWidths));
        System.out.println(formatRow(totalRow, columnWidths));
        System.out.println(formatSeparator(columnWidths));

        // Blank line before the next terminal message.
        System.out.println();
    }

    /**
     * Converts a country into formatted table cells.
     *
     * @return country values ready for display
     */
    private String[] toRow(Country country) {
        return new String[]{
                valueOrBlank(country.getCode()),
                valueOrBlank(country.getName()),
                valueOrBlank(country.getContinent()),
                valueOrBlank(country.getRegion()),
                formatPopulation(country.getPopulation()),
                valueOrBlank(country.getCapital())
        };
    }

    /**
     * Creates the summary row for a country report.
     *
     * @return row containing country count and total population
     */
    private String[] createTotalRow(List<Country> countries) {
        long totalPopulation = countries.stream()
                .mapToLong(Country::getPopulation)
                .sum();

        return new String[]{
                "Total",
                countries.size() + " Countries",
                "",
                "",
                formatPopulation(totalPopulation),
                ""
        };
    }

    /**
     * Formats a population with thousands separators.
     *
     * @return formatted population value
     */
    private String formatPopulation(long population) {
        return String.format(Locale.US, "%,d", population);
    }

    /**
     * Calculates country-table widths from headings and row values.
     *
     * @return display width for each country column
     */
    private int[] calculateColumnWidths(List<String[]> rows) {
        int[] widths = new int[HEADERS.length];

        // Start with header widths.
        for (int column = 0; column < HEADERS.length; column++) {
            widths[column] = HEADERS[column].length();
        }

        // Increase width if any data is longer than its heading.
        for (String[] row : rows) {
            for (int column = 0; column < row.length; column++) {
                widths[column] = Math.max(
                        widths[column],
                        row[column].length()
                );
            }
        }

        return widths;
    }

    /**
     * Creates a table separator for the supplied column widths.
     *
     * @return formatted separator line
     */
    private String formatSeparator(int[] columnWidths) {
        StringBuilder separator = new StringBuilder("+");

        for (int columnWidth : columnWidths) {
            separator.append("-".repeat(columnWidth + 2))
                    .append("+");
        }

        return separator.toString();
    }

    /**
     * Aligns and formats one row of table values.
     *
     * @return formatted table row
     */
    private String formatRow(String[] values, int[] columnWidths) {
        StringBuilder row = new StringBuilder("|");

        for (int column = 0; column < values.length; column++) {
            String value = values[column];

            row.append(" ");

            if (column == POPULATION_COLUMN) {
                // Right-align population values.
                row.append(" ".repeat(
                        columnWidths[column] - value.length()
                ));
                row.append(value);
            } else {
                // Left-align other columns.
                row.append(value);
                row.append(" ".repeat(
                        columnWidths[column] - value.length()
                ));
            }

            row.append(" |");
        }

        return row.toString();
    }

    /**
     * Replaces a null display value with an empty string.
     *
     * @return original value or an empty string
     */
    private String valueOrBlank(String value) {
        return value == null ? "" : value;
    }

    /**
     * Prints a city report with the default report name.
     */
    public void printCities(List<City> cities) {
        printCities("Cities", cities);
    }

    /**
     * Prints a named city report and its total population.
     */
    public void printCities(String reportName, List<City> cities) {
        System.out.println("Report for " + reportName);
        System.out.println();

        if (cities.isEmpty()) {
            System.out.println("No cities found.");
            System.out.println();
            return;
        }

        List<String[]> rows = new ArrayList<>();

        for (City city : cities) {
            rows.add(toCityRow(city));
        }

        String[] totalRow = createCityTotalRow(cities);

        List<String[]> rowsForWidthCalculation = new ArrayList<>(rows);
        rowsForWidthCalculation.add(totalRow);

        int[] columnWidths =
                calculateCityColumnWidths(rowsForWidthCalculation);

        System.out.println(formatSeparator(columnWidths));
        System.out.println(formatRow(CITY_HEADERS, columnWidths));
        System.out.println(formatSeparator(columnWidths));

        for (String[] row : rows) {
            System.out.println(formatRow(row, columnWidths));
        }

        System.out.println(formatSeparator(columnWidths));
        System.out.println(formatRow(totalRow, columnWidths));
        System.out.println(formatSeparator(columnWidths));

        System.out.println();
    }

    /**
     * Converts a city into formatted table cells.
     *
     * @return city values ready for display
     */
    private String[] toCityRow(City city) {
        return new String[]{
                valueOrBlank(city.getName()),
                valueOrBlank(city.getCountry()),
                valueOrBlank(city.getDistrict()),
                formatPopulation(city.getPopulation())
        };
    }

    /**
     * Creates the summary row for a city report.
     *
     * @return row containing city count and total population
     */
    private String[] createCityTotalRow(List<City> cities) {
        long totalPopulation = cities.stream()
                .mapToLong(City::getPopulation)
                .sum();

        return new String[]{
                "Total",
                cities.size() + " Cities",
                "",
                formatPopulation(totalPopulation)
        };
    }

    /**
     * Calculates city-table widths from headings and row values.
     *
     * @return display width for each city column
     */
    private int[] calculateCityColumnWidths(List<String[]> rows) {
        int[] widths = new int[CITY_HEADERS.length];

        for (int column = 0; column < CITY_HEADERS.length; column++) {
            widths[column] = CITY_HEADERS[column].length();
        }

        for (String[] row : rows) {
            for (int column = 0; column < row.length; column++) {
                widths[column] = Math.max(
                        widths[column],
                        row[column].length()
                );
            }
        }

        return widths;
    }

    private static final String[] CAPITAL_CITY_HEADERS = {
            "Name",
            "Country",
            "Population"
    };

    private static final int CAPITAL_CITY_POPULATION_COLUMN = 2;

    /**
     * Prints a capital-city report with the default report name.
     */
    public void printCapitalCities(List<CapitalCity> capitalCities) {
        printCapitalCities("Capital cities", capitalCities);
    }

    /**
     * Prints a named capital-city report and its total population.
     */
    public void printCapitalCities(
            String reportName,
            List<CapitalCity> capitalCities) {

        System.out.println("Report for " + reportName);
        System.out.println();

        if (capitalCities.isEmpty()) {
            System.out.println("No capital cities found.");
            System.out.println();
            return;
        }

        List<String[]> rows = new ArrayList<>();

        for (CapitalCity capitalCity : capitalCities) {
            rows.add(toCapitalCityRow(capitalCity));
        }

        String[] totalRow = createCapitalCityTotalRow(capitalCities);

        List<String[]> rowsForWidthCalculation = new ArrayList<>(rows);
        rowsForWidthCalculation.add(totalRow);

        int[] columnWidths =
                calculateCapitalCityColumnWidths(rowsForWidthCalculation);

        System.out.println(formatSeparator(columnWidths));
        System.out.println(formatCapitalCityRow(
                CAPITAL_CITY_HEADERS,
                columnWidths
        ));
        System.out.println(formatSeparator(columnWidths));

        for (String[] row : rows) {
            System.out.println(formatCapitalCityRow(row, columnWidths));
        }

        System.out.println(formatSeparator(columnWidths));
        System.out.println(formatCapitalCityRow(totalRow, columnWidths));
        System.out.println(formatSeparator(columnWidths));
        System.out.println();
    }

    /**
     * Converts a capital city into formatted table cells.
     *
     * @return capital-city values ready for display
     */
    private String[] toCapitalCityRow(CapitalCity capitalCity) {
        return new String[]{
                valueOrBlank(capitalCity.getName()),
                valueOrBlank(capitalCity.getCountry()),
                formatPopulation(capitalCity.getPopulation())
        };
    }

    /**
     * Creates the summary row for a capital-city report.
     *
     * @return row containing capital-city count and total population
     */
    private String[] createCapitalCityTotalRow(
            List<CapitalCity> capitalCities) {

        long totalPopulation = capitalCities.stream()
                .mapToLong(CapitalCity::getPopulation)
                .sum();

        return new String[]{
                "Total",
                capitalCities.size() + " Capital Cities",
                formatPopulation(totalPopulation)
        };
    }

    /**
     * Calculates capital-city table widths from headings and row values.
     *
     * @return display width for each capital-city column
     */
    private int[] calculateCapitalCityColumnWidths(List<String[]> rows) {
        int[] widths = new int[CAPITAL_CITY_HEADERS.length];

        for (int column = 0;
             column < CAPITAL_CITY_HEADERS.length;
             column++) {
            widths[column] = CAPITAL_CITY_HEADERS[column].length();
        }

        for (String[] row : rows) {
            for (int column = 0; column < row.length; column++) {
                widths[column] = Math.max(
                        widths[column],
                        row[column].length()
                );
            }
        }

        return widths;
    }

    /**
     * Aligns and formats one capital-city table row.
     *
     * @return formatted capital-city table row
     */
    private String formatCapitalCityRow(
            String[] values,
            int[] columnWidths) {

        StringBuilder row = new StringBuilder("|");

        for (int column = 0; column < values.length; column++) {
            String value = values[column];
            row.append(" ");

            if (column == CAPITAL_CITY_POPULATION_COLUMN) {
                row.append(" ".repeat(
                        columnWidths[column] - value.length()
                ));
                row.append(value);
            } else {
                row.append(value);
                row.append(" ".repeat(
                        columnWidths[column] - value.length()
                ));
            }

            row.append(" |");
        }

        return row.toString();
    }

}
