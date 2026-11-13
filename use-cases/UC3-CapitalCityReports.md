# USE CASE: Generate Capital City Reports (Requirements 17–22)

## CHARACTERISTIC INFORMATION

### Goal in Context
As a Demographic Analyst, I want to generate reports of capital cities organised by largest population to smallest across the world, a continent, or a region—with the option to filter by the top *N* populated capital cities—so that national capital populations can be compared across the organisation.

### Scope
DevOps Population Reporting System (Black-Box).

### Level
Primary task.

### Preconditions
* The MySQL `world` database is initialised and contains current country and capital city population data.
* The Demographic Analyst has access to the reporting application CLI.

### Success End Condition
A formatted Capital City Report is displayed to the Demographic Analyst, ordered from largest population to smallest, containing the required columns: `Name`, `Country`, and `Population`.

### Failed End Condition
No report is generated; the system outputs a readable validation or error message and exits safely.

### Primary Actor
Demographic Analyst.

### Trigger
The Demographic Analyst executes a capital city report command on the system.

## MAIN SUCCESS SCENARIO

1. The Demographic Analyst requests a Capital City Report and specifies the target geographic scope (World, Continent, or Region).
2. The system validates the supplied command arguments and scope parameters.
3. The system retrieves all capital cities matching the specified scope, organised from largest population to smallest.
4. The system extracts the required Capital City Report columns for each capital city: `Name`, `Country`, and `Population`.
5. The system formats and displays the Capital City Report table to the Demographic Analyst.

## EXTENSIONS

1. **Filter by Top 'N' Entries (`<<extend>>` Req 20–22):** At Step 1, the Demographic Analyst supplies a positive whole number *N* to view only the top *N* populated capital cities in the World (**Req 20**), a Continent (**Req 21**), or a Region (**Req 22**); the system validates *N* at Step 2 and limits Step 3 to the top *N* rows.
2. **Invalid or Unrecognised Scope (Step 2):** If the specified continent or region name is missing, blank, or unrecognised, the system outputs a validation error message, shows command usage, and exits with status code `1`.
3. **Invalid Top 'N' Argument (Step 2):** If *N* is zero, negative, or non-numeric, the system rejects the input, displays an error message, and exits with status code `1`.
4. **Database Connection Failure (Step 3):** If the database cannot be reached after the configured retry attempts, the system logs the connection failure and terminates safely.

## SUB-VARIATIONS

1. **Req 17 (World):** All the capital cities in the world organised by largest population to smallest.
2. **Req 18 (Continent):** All the capital cities in a continent organised by largest population to smallest.
3. **Req 19 (Region):** All the capital cities in a region organised by largest population to smallest.

## SCHEDULE

* **DUE DATE:** Release v0.4.0 — Capital City Reports.