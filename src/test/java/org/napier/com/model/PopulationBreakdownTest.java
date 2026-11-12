package org.napier.com.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PopulationBreakdownTest {

    @Test
    void storesBreakdownValues() {
        PopulationBreakdown breakdown = new PopulationBreakdown(
                "Asia",
                3700000000L,
                1200000000L
        );

        assertEquals("Asia", breakdown.getName());
        assertEquals(3700000000L, breakdown.getTotalPopulation());
        assertEquals(1200000000L, breakdown.getCityPopulation());
    }

    @Test
    void nonCityPopulationIsTotalMinusCity() {
        PopulationBreakdown breakdown = new PopulationBreakdown(
                "Europe",
                727000000L,
                300000000L
        );

        assertEquals(427000000L, breakdown.getNonCityPopulation());
    }

    @Test
    void handlesZeroPopulationWithoutError() {
        PopulationBreakdown breakdown = new PopulationBreakdown(
                "Antarctica",
                0L,
                0L
        );

        assertEquals(0L, breakdown.getTotalPopulation());
        assertEquals(0L, breakdown.getCityPopulation());
        assertEquals(0L, breakdown.getNonCityPopulation());
    }

    @Test
    void handlesContinentWithNoCities() {
        PopulationBreakdown breakdown = new PopulationBreakdown(
                "Example",
                1000L,
                0L
        );

        assertEquals(1000L, breakdown.getNonCityPopulation());
    }
}
