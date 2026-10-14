package org.napier.com.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CityTest {

    @Test
    void storesCityValues() {
        City city = new City(
                "Seoul",
                "South Korea",
                "Seoul",
                9981619L
        );

        assertEquals("Seoul", city.getName());
        assertEquals("South Korea", city.getCountry());
        assertEquals("Seoul", city.getDistrict());
        assertEquals(9981619L, city.getPopulation());
    }
}