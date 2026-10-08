package org.napier.com.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountryTest {

    @Test
    void storesCountryValues() {
        Country country = new Country(
                "GBR",
                "United Kingdom",
                "Europe",
                "British Islands",
                67200000L,
                "London"
        );

        assertEquals("GBR", country.getCode());
        assertEquals("United Kingdom", country.getName());
        assertEquals("Europe", country.getContinent());
        assertEquals("British Islands", country.getRegion());
        assertEquals(67200000L, country.getPopulation());
        assertEquals("London", country.getCapital());
    }
}