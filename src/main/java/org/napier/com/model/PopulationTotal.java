package org.napier.com.model;

/**
 * Holds the total population of a named geographical area.
 */
public class PopulationTotal {

    private final String name;
    private final long totalPopulation;

    public PopulationTotal(String name, long totalPopulation) {
        this.name = name;
        this.totalPopulation = totalPopulation;
    }

    public String getName() {
        return name;
    }

    public long getTotalPopulation() {
        return totalPopulation;
    }
}