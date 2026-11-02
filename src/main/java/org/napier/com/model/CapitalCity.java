package org.napier.com.model;

/**
 * Immutable capital-city data used by capital-city reports.
 */
public class CapitalCity {
    private final String name;
    private final String country;
    private final long population;

    /**
     * Creates a capital-city report row.
     */
    public CapitalCity(String name, String country, long population) {
        this.name = name;
        this.country = country;
        this.population = population;
    }

    /**
     * Returns the capital-city name.
     *
     * @return capital-city name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the country name.
     *
     * @return country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * Returns the capital-city population.
     *
     * @return capital-city population
     */
    public long getPopulation() {
        return population;
    }
}
