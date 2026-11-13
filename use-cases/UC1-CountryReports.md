# USE CASE: Generate Country Reports (Requirements 1–6)

## CHARACTERISTIC INFORMATION

### Goal in Context
As a Demographic Analyst, I want to generate reports of countries organised by largest population to smallest across the world, a continent, or a region—with the option to filter by the top *N* populated countries—so that I have easy access to country population information for the organisation.

### Scope
DevOps Population Reporting System (Black-Box).

### Level
Primary task.

### Preconditions
* The MySQL `world` database is initialised and contains current country and capital city population data.
* The Demographic Analyst has access to the reporting application CLI.

### Success End Condition
A formatted Country Report is displayed to the Demographic Analyst, ordered from largest population to smallest, containing the required columns: `Code`, `Name`, `Continent`, `Region`, `Population`, and `Capital`.

### Failed End Condition
No report is generated; the system outputs a readable validation or error message and exits safely.

### Primary Actor
Demographic Analyst.

### Trigger
The Demographic Analyst executes a country report command on the system.

## MAIN SUCCESS SCENARIO

1. The Demographic Analyst requests a Country Report and specifies the target geographic scope (World, Continent, or Region).
2. The system validates the supplied command arguments and scope parameters.
3. The system retrieves all countries matching the specified scope, organised from largest population to smallest.
4. The system extracts the required Country Report columns for each country: `Code`, `Name`, `Continent`, `Region`, `Population`, and `Capital`.
5. The system formats and displays the Country Report table to the Demographic Analyst.

## EXTENSIONS

1. **Filter by Top 'N' Entries (`<<extend>>` Req 4–6):** At Step 1, the Demographic Analyst supplies a positive whole number *N* to view only the top *N* populated countries in the World (**Req 4**), a Continent (**Req 5**), or a Region (**Req 6**); the system validates *N* at Step 2 and limits Step 3 to the top *N* rows.
2. **Invalid or Unrecognised Scope (Step 2):** If the continent or region name is missing, blank, or unrecognised, the system displays a readable validation error message, shows command usage, and exits with status code `1`.
3. **Invalid Top 'N' Argument (Step 2):** If *N* is zero, negative, or non-numeric, the system rejects the input, displays an error message, and exits with status code `1`.
4. **Database Connection Failure (Step 3):** If the database cannot be reached after the configured retry attempts, the system logs the connection failure and terminates safely.

## SUB-VARIATIONS

1. **Req 1 (World):** All the countries in the world organised by largest population to smallest.
2. **Req 2 (Continent):** All the countries in a continent organised by largest population to smallest.
3. **Req 3 (Region):** All the countries in a region organised by largest population to smallest.

## SCHEDULE

* **DUE DATE:** Release v0.2 (8 October 2026) / Release v0.3.0 (14 October 2026).