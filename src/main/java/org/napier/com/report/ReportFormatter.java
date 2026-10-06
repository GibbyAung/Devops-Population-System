package org.napier.com.report;

import org.napier.com.model.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public void printCountries(List<Country> countries) {
        printCountries("Countries", countries);
    }

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

    private String formatPopulation(long population) {
        return String.format(Locale.US, "%,d", population);
    }

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

    private String formatSeparator(int[] columnWidths) {
        StringBuilder separator = new StringBuilder("+");

        for (int columnWidth : columnWidths) {
            separator.append("-".repeat(columnWidth + 2))
                    .append("+");
        }

        return separator.toString();
    }

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

    private String valueOrBlank(String value) {
        return value == null ? "" : value;
    }
}