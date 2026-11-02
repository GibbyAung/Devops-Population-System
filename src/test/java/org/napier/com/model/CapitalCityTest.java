package org.napier.com.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CapitalCityTest {

    @Test
    void storesCapitalCityReportValues() {
        CapitalCity capitalCity = new CapitalCity(
                "Tokyo",
                "Japan",
                7980230L
        );

        assertEquals("Tokyo", capitalCity.getName());
        assertEquals("Japan", capitalCity.getCountry());
        assertEquals(7980230L, capitalCity.getPopulation());
    }
}
