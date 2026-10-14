package org.napier.com.model;

/**
 * Immutable city data used by city reports.
 */
public class City {
    private final String name;
    private final String country;
    private final String district;
    private final long population;

    /**
     * Creates a city report row.
     */
    public City(String name, String country, String district, long population) {
        this.name = name;
        this.country = country;
        this.district = district;
        this.population = population;
    }

    /**
     * Returns the city name.
     *
     * @return city name
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
     * Returns the city district.
     *
     * @return city district
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Returns the city population.
     *
     * @return city population
     */
    public long getPopulation() {
        return population;
    }
}
