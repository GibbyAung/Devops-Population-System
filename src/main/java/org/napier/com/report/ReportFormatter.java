package org.napier.com.report;

import org.napier.com.model.Country;

import java.util.List;

public class ReportFormatter {
    public void printCountries(List<Country> countries) {
        System.out.println("Code | Name | Continent | Region | Population | Capital");

        if (countries.isEmpty()) {
            System.out.println("No countries found.");
            return;
        }

        for (Country country : countries) {
            System.out.printf("%s | %s | %s | %s | %d | %s%n",
                    country.getCode(), country.getName(), country.getContinent(),
                    country.getRegion(), country.getPopulation(), country.getCapital());
        }
    }
}