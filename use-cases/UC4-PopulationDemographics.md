# USE CASE: View Population Demographics (Requirements 23–31)

## CHARACTERISTIC INFORMATION

### Goal in Context
As a Demographic Analyst, I want to generate population breakdown reports showing total population, city population (with percentage), and non-city population (with percentage) by continent, region, and country, as well as total population lookups for the world, a continent, a region, a country, a district, or a city, so that demographic summaries and totals are accessible to the organisation.

### Scope
DevOps Population Reporting System (Black-Box).

### Level
Primary task.

### Preconditions
* The MySQL `world` database is initialised and contains current country, district, and city population data.
* The Demographic Analyst has access to the reporting application CLI.

### Success End Condition
* **Requirements 23–25 (Population Breakdown Reports):** A formatted Population Report is displayed ordered from largest total population to smallest, containing the columns: Name of the `Continent`/`Region`/`Country`, `Total Population`, `City Population` (including `%`), and `Non-City Population` (including `%`).
* **Requirements 26–31 (Total Population Lookups):** The exact total population for the requested scope (`World`, `Continent`, `Region`, `Country`, `District`, or `City`) is displayed.

### Failed End Condition
No report is generated; the system outputs a readable validation or error message and exits with status code `1`.

### Primary Actor
Demographic Analyst.

### Trigger
The Demographic Analyst executes a population breakdown or total population command on the system.

## MAIN SUCCESS SCENARIO

1. The Demographic Analyst requests a population breakdown report or a total population lookup and provides the target scope name when querying a specific continent, region, country, district, or city.
2. The system validates the command arguments and target scope parameters.
3. The system retrieves and aggregates the population figures for the requested scope:
   * For **Population Breakdown Reports (Req 23–25):** Calculates total population, total population living in cities (with `%`), and total population not living in cities (with `%`), ordered by total population from largest to smallest.
   * For **Total Population Lookups (Req 26–31):** Calculates the total population of the specified geographic scope.
4. The system compiles the required report columns and formats the output.
5. The system displays the report to the Demographic Analyst.

## EXTENSIONS

1. **Zero Total Population Scope (Step 3):** If a continent, region, or country has a total population of `0` (such as Antarctica or Micronesia/Caribbean), the system guards against division-by-zero and formats the city and non-city populations as `0 (0.00%)`.
2. **Missing, Blank, or Unknown Target Name (Step 2):** If the target continent, region, country, district, or city name is omitted, blank, or unrecognised, or if extra arguments are supplied, the system outputs a readable error message, displays usage guidance, and exits with status code `1`.
3. **Database Connection Failure (Step 3):** If the database cannot be reached after the configured retry attempts, the system logs the connection failure and terminates safely.

## SUB-VARIATIONS

1. **Req 23 (Continent Breakdown):** The population of people, people living in cities (%), and people not living in cities (%) in each continent.
2. **Req 24 (Region Breakdown):** The population of people, people living in cities (%), and people not living in cities (%) in each region.
3. **Req 25 (Country Breakdown):** The population of people, people living in cities (%), and people not living in cities (%) in each country.
4. **Req 26 (World Total):** The population of the world.
5. **Req 27 (Continent Total):** The population of a continent.
6. **Req 28 (Region Total):** The population of a region.
7. **Req 29 (Country Total):** The population of a country.
8. **Req 30 (District Total):** The population of a district.
9. **Req 31 (City Total):** The population of a city.

## SCHEDULE

* **DUE DATE:** Release v0.5.0 — Sprint 5 Population Reports.