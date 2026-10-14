package org.napier.com.model;

/**
 * Immutable country data used by country reports.
 */
public class Country {
    private final String code;
    private final String name;
    private final String continent;
    private final String region;
    private final long population;
    private final String capital;

    /**
     * Creates a country report row.
     */
    public Country(String code, String name, String continent, String region,
                   long population, String capital) {
        this.code = code;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.population = population;
        this.capital = capital;
    }

    /**
     * Returns the ISO country code.
     *
     * @return ISO country code
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the country name.
     *
     * @return country name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the country continent.
     *
     * @return country continent
     */
    public String getContinent() {
        return continent;
    }

    /**
     * Returns the country region.
     *
     * @return country region
     */
    public String getRegion() {
        return region;
    }

    /**
     * Returns the country population.
     *
     * @return country population
     */
    public long getPopulation() {
        return population;
    }

    /**
     * Returns the capital city name.
     *
     * @return capital city name
     */
    public String getCapital() {
        return capital;
    }
}
