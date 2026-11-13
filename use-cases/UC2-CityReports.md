# USE CASE: Generate City Reports (Requirements 7–16)

## CHARACTERISTIC INFORMATION

### Goal in Context
As a Demographic Analyst, I want to generate reports of cities organised by largest population to smallest across the world, a continent, a region, a country, or a district—with the option to filter by the top *N* populated cities—so that urban population information can be accessed across the organisation.

### Scope
DevOps Population Reporting System (Black-Box).

### Level
Primary task.

### Preconditions
* The MySQL `world` database is initialised and contains current city and country population data.
* The Demographic Analyst has access to the reporting application CLI.

### Success End Condition
A formatted City Report is displayed to the Demographic Analyst, ordered from largest population to smallest, containing the required columns: `Name`, `Country`, `District`, and `Population`.

### Failed End Condition
No report is generated; the system outputs a readable validation or error message and exits safely.

### Primary Actor
Demographic Analyst.

### Trigger
The Demographic Analyst executes a city report command on the system.

## MAIN SUCCESS SCENARIO

1. The Demographic Analyst requests a City Report and specifies the target geographic scope (World, Continent, Region, Country, or District).
2. The system validates the supplied command arguments and scope parameters.
3. The system retrieves all cities matching the specified scope, organised from largest population to smallest.
4. The system extracts the required City Report columns for each city: `Name`, `Country`, `District`, and `Population`.
5. The system formats and displays the City Report table to the Demographic Analyst.

## EXTENSIONS

1. **Filter by Top 'N' Entries (`<<extend>>` Req 12–16):** At Step 1, the Demographic Analyst supplies a positive whole number *N* to view only the top *N* populated cities in the World (**Req 12**), a Continent (**Req 13**), a Region (**Req 14**), a Country (**Req 15**), or a District (**Req 16**); the system validates *N* at Step 2 and limits Step 3 to the top *N* rows.
2. **Invalid or Unrecognised Scope (Step 2):** If the specified continent, region, country, or district name is missing, blank, or unrecognised, the system outputs a validation error message, shows command usage, and exits with status code `1`.
3. **Invalid Top 'N' Argument (Step 2):** If *N* is zero, negative, or non-numeric, the system rejects the input, displays an error message, and exits with status code `1`.
4. **Database Connection Failure (Step 3):** If the database cannot be reached after the configured retry attempts, the system logs the connection failure and terminates safely.

## SUB-VARIATIONS

1. **Req 7 (World):** All the cities in the world organised by largest population to smallest.
2. **Req 8 (Continent):** All the cities in a continent organised by largest population to smallest.
3. **Req 9 (Region):** All the cities in a region organised by largest population to smallest.
4. **Req 10 (Country):** All the cities in a country organised by largest population to smallest.
5. **Req 11 (District):** All the cities in a district organised by largest population to smallest.

## SCHEDULE

* **DUE DATE:** Release v0.3.0 — Requirements 1–16 (14 October 2026).