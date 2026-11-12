package org.napier.com.report;

import org.junit.jupiter.api.Test;
import org.napier.com.model.Country;
import org.napier.com.model.CapitalCity;
import org.napier.com.model.PopulationBreakdown;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.napier.com.model.PopulationTotal;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.napier.com.model.PopulationTotal;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportFormatterTest {

    @Test
    void printsRequiredHeadingsAndCountry() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try {
            System.setOut(new PrintStream(output));

            new ReportFormatter().printCountries(List.of(
                    new Country(
                            "GBR",
                            "United Kingdom",
                            "Europe",
                            "British Islands",
                            67200000L,
                            "London"
                    )
            ));
        } finally {
            System.setOut(original);
        }

        String text = output.toString(StandardCharsets.UTF_8);

        assertTrue(text.contains("Code"));
        assertTrue(text.contains("Name"));
        assertTrue(text.contains("Continent"));
        assertTrue(text.contains("Region"));
        assertTrue(text.contains("Population"));
        assertTrue(text.contains("Capital"));
        assertTrue(text.contains("London"));
    }

    @Test
    void printsRequiredCityHeadings() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try {
            System.setOut(new PrintStream(output));

            new ReportFormatter().printCities(List.of(
                    new org.napier.com.model.City(
                            "Seoul",
                            "South Korea",
                            "Seoul",
                            9981619L
                    )
            ));
        } finally {
            System.setOut(original);
        }

        String text = output.toString(StandardCharsets.UTF_8);

        assertTrue(text.contains("Name"));
        assertTrue(text.contains("Country"));
        assertTrue(text.contains("District"));
        assertTrue(text.contains("Population"));
        assertTrue(text.contains("Seoul"));
    }

    @Test
    void printsContinentPopulationBreakdownWithPercentages() {
        String text = captureContinentBreakdown(List.of(
                new PopulationBreakdown(
                        "Example",
                        1000000L,
                        456789L
                )
        ));

        assertTrue(text.contains("Continent"));
        assertTrue(text.contains("Total Population"));
        assertTrue(text.contains("City Population"));
        assertTrue(text.contains("Non-City Population"));
        assertTrue(text.contains("1,000,000"));
        assertTrue(text.contains("456,789 (45.68%)"));
        assertTrue(text.contains("543,211 (54.32%)"));
        assertTrue(text.contains("Total"));
    }

    @Test
    void printsRegionPopulationBreakdownHeaders() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try {
            System.setOut(new PrintStream(output));

            new ReportFormatter().printRegionPopulationBreakdowns(
                    "Population breakdown by region",
                    List.of(
                            new PopulationBreakdown(
                                    "Eastern Asia",
                                    1500000000L,
                                    700000000L
                            )
                    )
            );
        } finally {
            System.setOut(original);
        }

        String text = output.toString(StandardCharsets.UTF_8);

        assertTrue(text.contains("Region"));
        assertTrue(text.contains("Eastern Asia"));
        assertTrue(text.contains("1,500,000,000"));
        assertTrue(text.contains("700,000,000 (46.67%)"));
        assertTrue(text.contains("800,000,000 (53.33%)"));
    }

    @Test
    void printsCountryPopulationBreakdownWithPercentages() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try {
            System.setOut(new PrintStream(output));
            new ReportFormatter().printCountryPopulationBreakdowns(
                    "Population breakdown by country",
                    List.of(new PopulationBreakdown(
                            "Example", 1000L, 250L)));
        } finally {
            System.setOut(original);
        }

        String text = output.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("Country"));
        assertTrue(text.contains("Example"));
        assertTrue(text.contains("250 (25.00%)"));
        assertTrue(text.contains("750 (75.00%)"));
    }

    @Test
    void printsWorldTotalPopulationAsLabelledLong() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try {
            System.setOut(new PrintStream(output));
            new ReportFormatter().printWorldTotalPopulation(9_000_000_000L);
        } finally {
            System.setOut(original);
        }

        assertTrue(output.toString(StandardCharsets.UTF_8).contains(
                "World total population: 9,000,000,000"));
    }

    @Test
    void zeroTotalPopulationReportsZeroPercentWithoutError() {
        String text = captureContinentBreakdown(List.of(
                new PopulationBreakdown("Antarctica", 0L, 0L)
        ));

        assertTrue(text.contains("0 (0.00%)"));
        assertTrue(text.contains("Antarctica"));
    }

    @Test
    void emptyBreakdownListShowsNoDataMessage() {
        String text = captureContinentBreakdown(List.of());

        assertTrue(text.contains(
                "Report for Population breakdown by continent"
        ));
        assertTrue(text.contains("No population data found."));
    }

    private String captureContinentBreakdown(
            List<PopulationBreakdown> breakdowns) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try {
            System.setOut(new PrintStream(output));

            new ReportFormatter().printContinentPopulationBreakdowns(
                    "Population breakdown by continent",
                    breakdowns
            );
        } finally {
            System.setOut(original);
        }

        return output.toString(StandardCharsets.UTF_8);
    }
    @ParameterizedTest
    @ValueSource(strings = {"Continent", "Region"})
    void printsPopulationTotalWithLongValue(String scopeLabel) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try {
            System.setOut(new PrintStream(output));

            new ReportFormatter().printPopulationTotal(
                    scopeLabel,
                    new PopulationTotal(
                            "Example",
                            3700000000L
                    )
            );
        } finally {
            System.setOut(original);
        }

        String text = output.toString(StandardCharsets.UTF_8);

        assertTrue(text.contains(scopeLabel));
        assertTrue(text.contains("Total Population"));
        assertTrue(text.contains("Example"));
        assertTrue(text.contains("3,700,000,000"));
    }
    @ParameterizedTest
    @ValueSource(strings = {"Country", "District"})
    void printsCountryAndDistrictPopulationTotalLabels(String scopeLabel) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try {
            System.setOut(new PrintStream(output));

            new ReportFormatter().printPopulationTotal(
                    scopeLabel,
                    new PopulationTotal("Example", 3500000000L)
            );
        } finally {
            System.setOut(original);
        }

        String text = output.toString(StandardCharsets.UTF_8);

        assertTrue(text.contains(scopeLabel));
        assertTrue(text.contains("Total Population"));
        assertTrue(text.contains("Example"));
        assertTrue(text.contains("3,500,000,000"));
    }

    @Test
    void printsRequiredCapitalCityHeadings() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try {
            System.setOut(new PrintStream(output));
            new ReportFormatter().printCapitalCities(List.of(
                    new CapitalCity("Tokyo", "Japan", 7980230L)
            ));
        } finally {
            System.setOut(original);
        }

        String text = output.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("Name"));
        assertTrue(text.contains("Country"));
        assertTrue(text.contains("Population"));
        assertTrue(text.contains("Tokyo"));
        assertTrue(text.contains("Japan"));
    }
}
