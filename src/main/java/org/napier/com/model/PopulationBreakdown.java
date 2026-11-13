package org.napier.com.model;

/**
 * Immutable population breakdown row used by continent and region reports.
 */
public class PopulationBreakdown {
    private final String name;
    private final long totalPopulation;
    private final long cityPopulation;
    private final long nonCityPopulation;

    /**
     * Creates a population breakdown row and derives the non-city
     * population as total population minus the city population.
     */
    public PopulationBreakdown(
            String name,
            long totalPopulation,
            long cityPopulation) {
        this.name = name;
        this.totalPopulation = totalPopulation;
        this.cityPopulation = cityPopulation;
        this.nonCityPopulation = totalPopulation - cityPopulation;
    }

    /**
     * Returns the continent or region name.
     *
     * @return continent or region name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the total population of the continent or region.
     *
     * @return total population
     */
    public long getTotalPopulation() {
        return totalPopulation;
    }

    /**
     * Returns the population living in cities.
     *
     * @return city population
     */
    public long getCityPopulation() {
        return cityPopulation;
    }

    /**
     * Returns the population not living in cities.
     *
     * @return non-city population
     */
    public long getNonCityPopulation() {
        return nonCityPopulation;
    }
}
