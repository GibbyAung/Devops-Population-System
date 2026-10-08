package org.napier.com.report;

import org.junit.jupiter.api.Test;
import org.napier.com.model.Country;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
}